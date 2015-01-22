package me.chanjar.weixin.cp.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.StandardSessionManager;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.*;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpTag;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;

public class WxCpServiceImpl implements WxCpService {

  protected final Logger log = LoggerFactory.getLogger(WxCpServiceImpl.class);

  /**
   * 全局的是否正在刷新access token的锁
   */
  protected static final Object GLOBAL_ACCESS_TOKEN_REFRESH_LOCK = new Object();

  protected WxCpConfigStorage wxCpConfigStorage;

  protected CloseableHttpClient httpClient;

  protected HttpHost httpProxy;

  private int retrySleepMillis = 1000;

  private int maxRetryTimes = 5;

  protected WxSessionManager sessionManager = new StandardSessionManager();

  public boolean checkSignature(String msgSignature, String timestamp, String nonce, String data) {
    try {
      return SHA1.gen(wxCpConfigStorage.getToken(), timestamp, nonce, data).equals(msgSignature);
    } catch (Exception e) {
      return false;
    }
  }

  public void userAuthenticated(String userId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?userid=" + userId;
    execute(new SimpleGetRequestExecutor(), url, null);
  }

  public String getAccessToken() throws WxErrorException {
    return getAccessToken(false);
  }

  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    if (forceRefresh) {
      wxCpConfigStorage.expireAccessToken();
    }
    if (wxCpConfigStorage.isAccessTokenExpired()) {
      synchronized (GLOBAL_ACCESS_TOKEN_REFRESH_LOCK) {
        if (wxCpConfigStorage.isAccessTokenExpired()) {
          String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?"
              + "&corpid=" + wxCpConfigStorage.getCorpId()
              + "&corpsecret=" + wxCpConfigStorage.getCorpSecret();
          try {
            HttpGet httpGet = new HttpGet(url);
            if (httpProxy != null) {
              RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
              httpGet.setConfig(config);
            }
            CloseableHttpClient httpclient = getHttpclient();
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String resultContent = new BasicResponseHandler().handleResponse(response);
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
              throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            wxCpConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
          } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
    return wxCpConfigStorage.getAccessToken();
  }

  public void messageSend(WxCpMessage message) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
    execute(new SimplePostRequestExecutor(), url, message.toJson());
  }

  public void menuCreate(WxMenu menu) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?agentid=" + wxCpConfigStorage.getAgentId();
    execute(new SimplePostRequestExecutor(), url, menu.toJson());
  }

  public void menuDelete() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?agentid=" + wxCpConfigStorage.getAgentId();
    execute(new SimpleGetRequestExecutor(), url, null);
  }

  public WxMenu menuGet() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/get?agentid=" + wxCpConfigStorage.getAgentId();
    try {
      String resultContent = execute(new SimpleGetRequestExecutor(), url, null);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据
      if (e.getError().getErrorCode() == 46003) {
        return null;
      }
      throw e;
    }
  }

  public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream)
      throws WxErrorException, IOException {
    return mediaUpload(mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
  }

  public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?type=" + mediaType;
    return execute(new MediaUploadRequestExecutor(), url, file);
  }

  public File mediaDownload(String media_id) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get";
    return execute(new MediaDownloadRequestExecutor(), url, "media_id=" + media_id);
  }


  public Integer departCreate(WxCpDepart depart) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/create";
    String responseContent = execute(
        new SimplePostRequestExecutor(),
        url,
        depart.toJson());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return GsonHelper.getAsInteger(tmpJsonElement.getAsJsonObject().get("id"));
  }

  public void departUpdate(WxCpDepart group) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/update";
    execute(new SimplePostRequestExecutor(), url, group.toJson());
  }

  public void departDelete(Integer departId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?id=" + departId;
    execute(new SimpleGetRequestExecutor(), url, null);
  }

  public List<WxCpDepart> departGet() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
    String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
    /*
     * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} }
     * 查询时返回的是 { groups : [ { id : ..., name : ..., count : ... }, ... ] }
     */
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("department"),
            new TypeToken<List<WxCpDepart>>() { }.getType()
        );
  }

  @Override
  public void userCreate(WxCpUser user) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
    execute(new SimplePostRequestExecutor(), url, user.toJson());
  }

  @Override
  public void userUpdate(WxCpUser user) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/update";
    execute(new SimplePostRequestExecutor(), url, user.toJson());
  }

  @Override
  public void userDelete(String userid) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?userid=" + userid;
    execute(new SimpleGetRequestExecutor(), url, null);
  }

  @Override
  public WxCpUser userGet(String userid) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?userid=" + userid;
    String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
    return WxCpUser.fromJson(responseContent);
  }

  @Override
  public List<WxCpUser> departGetUsers(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?department_id=" + departId;
    String params = "";
    if (fetchChild != null) {
      params += "&fetch_child=" + (fetchChild ? "1" : "0");
    }
    if (status != null) {
      params += "&status=" + status;
    } else {
      params += "&status=0";
    }

    String responseContent = execute(new SimpleGetRequestExecutor(), url, params);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("userlist"),
            new TypeToken<List<WxCpUser>>() { }.getType()
        );
  }

  @Override
  public String tagCreate(String tagName) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/create";
    JsonObject o = new JsonObject();
    o.addProperty("tagname", tagName);
    String responseContent = execute(new SimplePostRequestExecutor(), url, o.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return tmpJsonElement.getAsJsonObject().get("tagid").getAsString();
  }

  @Override
  public void tagUpdate(String tagId, String tagName) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/update";
    JsonObject o = new JsonObject();
    o.addProperty("tagid", tagId);
    o.addProperty("tagname", tagName);
    execute(new SimplePostRequestExecutor(), url, o.toString());
  }

  @Override
  public void tagDelete(String tagId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/delete?tagid=" + tagId;
    execute(new SimpleGetRequestExecutor(), url, null);
  }

  @Override
  public List<WxCpTag> tagGet() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/list";
    String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("taglist"),
            new TypeToken<List<WxCpTag>>() { }.getType()
        );
  }

  @Override
  public List<WxCpUser> tagGetUsers(String tagId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/get?tagid=" + tagId;
    String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("userlist"),
            new TypeToken<List<WxCpUser>>() { }.getType()
        );
  }

  @Override
  public void tagAddUsers(String tagId, List<String> userIds) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("tagid", tagId);
    JsonArray jsonArray = new JsonArray();
    for (String userId : userIds) {
      jsonArray.add(new JsonPrimitive(userId));
    }
    jsonObject.add("userlist", jsonArray);
    execute(new SimplePostRequestExecutor(), url, jsonObject.toString());
  }

  @Override
  public void tagRemoveUsers(String tagId, List<String> userIds) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("tagid", tagId);
    JsonArray jsonArray = new JsonArray();
    for (String userId : userIds) {
      jsonArray.add(new JsonPrimitive(userId));
    }
    jsonObject.add("userlist", jsonArray);
    execute(new SimplePostRequestExecutor(), url, jsonObject.toString());
  }

  @Override
  public String oauth2buildAuthorizationUrl(String state) {
    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" ;
    url += "appid=" + wxCpConfigStorage.getCorpId();
    url += "&redirect_uri=" + URIUtil.encodeURIComponent(wxCpConfigStorage.getOauth2redirectUri());
    url += "&response_type=code";
    url += "&scope=snsapi_base";
    if (state != null) {
      url += "&state=" + state;
    }
    url += "#wechat_redirect";
    return url;
  }

  @Override
  public String[] oauth2getUserInfo(String code) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?";
    url += "access_token=" + wxCpConfigStorage.getAccessToken();
    url += "&code=" + code;
    url += "&agendid=" + wxCpConfigStorage.getAgentId();

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
      JsonElement je = Streams.parse(new JsonReader(new StringReader(responseText)));
      JsonObject jo = je.getAsJsonObject();
      return new String[] {GsonHelper.getString(jo, "UserId"), GsonHelper.getString(jo, "DeviceId")};
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public String get(String url, String queryParam) throws WxErrorException {
    return execute(new SimpleGetRequestExecutor(), url, queryParam);
  }

  public String post(String url, String postData) throws WxErrorException {
    return execute(new SimplePostRequestExecutor(), url, postData);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   *
   * @param executor
   * @param uri
   * @param data
   * @return
   * @throws WxErrorException
   */
  public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
    int retryTimes = 0;
    do {
      try {
        return executeInternal(executor, uri, data);
      } catch (WxErrorException e) {
        WxError error = e.getError();
        /**
         * -1 系统繁忙, 1000ms后重试
         */
        if (error.getErrorCode() == -1) {
          int sleepMillis = retrySleepMillis * (1 << retryTimes);
          try {
            log.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
            Thread.sleep(sleepMillis);
          } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
          }
        } else {
          throw e;
        }
      }
    } while(++retryTimes < maxRetryTimes);

    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

  protected <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
    String accessToken = getAccessToken(false);

    String uriWithAccessToken = uri;
    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

    try {
      return executor.execute(getHttpclient(), httpProxy, uriWithAccessToken, data);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        wxCpConfigStorage.expireAccessToken();
        return execute(executor, uri, data);
      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return null;
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  protected CloseableHttpClient getHttpclient() {
    return httpClient;
  }

  public void setWxCpConfigStorage(WxCpConfigStorage wxConfigProvider) {
    this.wxCpConfigStorage = wxConfigProvider;

    String http_proxy_host = wxCpConfigStorage.getHttp_proxy_host();
    int http_proxy_port = wxCpConfigStorage.getHttp_proxy_port();
    String http_proxy_username = wxCpConfigStorage.getHttp_proxy_username();
    String http_proxy_password = wxCpConfigStorage.getHttp_proxy_password();

    if(StringUtils.isNotBlank(http_proxy_host)) {
      // 使用代理服务器
      if(StringUtils.isNotBlank(http_proxy_username)) {
        // 需要用户认证的代理服务器
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(http_proxy_host, http_proxy_port),
            new UsernamePasswordCredentials(http_proxy_username, http_proxy_password));
        httpClient = HttpClients
            .custom()
            .setDefaultCredentialsProvider(credsProvider)
            .build();
      } else {
        // 无需用户认证的代理服务器
        httpClient = HttpClients.createDefault();
      }
      httpProxy = new HttpHost(http_proxy_host, http_proxy_port);
    } else {
      httpClient = HttpClients.createDefault();
    }
  }

  @Override
  public void setRetrySleepMillis(int retrySleepMillis) {
    this.retrySleepMillis = retrySleepMillis;
  }


  @Override
  public void setMaxRetryTimes(int maxRetryTimes) {
    this.maxRetryTimes = maxRetryTimes;
  }

  @Override
  public WxSession getSession(String id) {
    if (sessionManager == null) {
      return null;
    }
    return sessionManager.getSession(id);
  }

  @Override
  public WxSession getSession(String id, boolean create) {
    if (sessionManager == null) {
      return null;
    }
    return sessionManager.getSession(id, create);
  }


  @Override
  public void setSessionManager(WxSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

}

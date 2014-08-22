package chanjarster.weixin.api;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import chanjarster.weixin.bean.WxAccessToken;
import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxError;
import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.util.Utf8ResponseHandler;

public class WxServiceImpl implements WxService {

  /**
   * 全局的是否正在刷新Access Token的flag
   * true: 正在刷新
   * false: 没有刷新
   */
  protected static final AtomicBoolean GLOBAL_ACCESS_TOKEN_REFRESH_FLAG = new AtomicBoolean(false);
  
  protected static final CloseableHttpClient httpclient = HttpClients.createDefault();
  
  protected WxConfigStorage wxConfigProvider;
  
  public void refreshAccessToken() throws WxErrorException {
    if (!GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.getAndSet(true)) {
      try {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
            + "&appid=" + wxConfigProvider.getAppId() 
            + "&secret=" + wxConfigProvider.getSecret()
            ;
        try {
          HttpGet httpGet = new HttpGet(url);
          CloseableHttpResponse response = httpclient.execute(httpGet);
          String resultContent = new BasicResponseHandler().handleResponse(response);
          WxError error = WxError.fromJson(resultContent);
          if (error.getErrcode() != 0) {
            throw new WxErrorException(error);
          }
          WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
          wxConfigProvider.updateAccessToken(accessToken.getAccess_token(), accessToken.getExpires_in());
        } catch (ClientProtocolException e) {
          throw new RuntimeException(e);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } finally {
        GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.set(false);
      }
    } else {
      // 每隔100ms检查一下是否刷新完毕了
      while (GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.get()) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
        }
      }
      // 刷新完毕了，就没他什么事儿了
    }
  }
  
  public String sendCustomMessage(WxCustomMessage message) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    return post(url, message.toJson());
  }
  
  public String createMenu(WxMenu menu) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
    return post(url, menu.toJson());
  }
  
  public String deleteMenu() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    return get(url, null);
  }

  public WxMenu getMenu() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
    try {
      String resultContent = get(url, null);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据
      if (e.getError().getErrcode() == 46003) {
        return null;
      }
      throw e;
    }
  }

  protected String post(String uri, String data) throws WxErrorException {
    return execute("POST", uri, data);
  }
  
  protected String get(String uri, String data) throws WxErrorException {
    return execute("GET", uri, data);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   * @param request
   * @return 微信服务端返回的结果
   * @throws WxErrorException 
   */
  protected String execute(String method, String uri, String data) throws WxErrorException {
    if (StringUtils.isBlank(wxConfigProvider.getAccessToken())) {
      refreshAccessToken();
    }
    String accessToken = wxConfigProvider.getAccessToken();
    
    String uriWithAccessToken = uri;
    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;
    
    try {
      String resultContent = null;
      if ("POST".equals(method)) {
        HttpPost httpPost = new HttpPost(uriWithAccessToken);
        if (data != null) {
          StringEntity entity = new StringEntity(data, Consts.UTF_8);
          httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);
        resultContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      } else if ("GET".equals(method)) {
        if (data != null) {
          uriWithAccessToken += uriWithAccessToken.endsWith("&") ? data : '&' + data;
        }
        HttpGet httpGet = new HttpGet(uriWithAccessToken);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        response.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        resultContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      }
      
      WxError error = WxError.fromJson(resultContent);
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       */
      if (error.getErrcode() == 42001 || error.getErrcode() == 40001) {
        refreshAccessToken();
        return execute(method, uri, data);
      }
      if (error.getErrcode() != 0) {
        throw new WxErrorException(error);
      }
      return resultContent;
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public void setWxConfigProvider(WxConfigStorage wxConfigProvider) {
    this.wxConfigProvider = wxConfigProvider;
  }

}

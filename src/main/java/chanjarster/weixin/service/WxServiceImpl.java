package chanjarster.weixin.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.in.WxAccessToken;
import chanjarster.weixin.in.WxError;
import chanjarster.weixin.out.WxCustomMessage;
import chanjarster.weixin.out.WxMenu;

public class WxServiceImpl implements WxService {

  /**
   * 全局的是否正在刷新Access Token的flag
   * true: 正在刷新
   * false: 没有刷新
   */
  protected static final AtomicBoolean GLOBAL_ACCESS_TOKEN_REFRESH_FLAG = new AtomicBoolean(false);
  
  protected static final CloseableHttpClient httpclient = HttpClients.createDefault();
  
  protected static final Charset UTF8 = Charset.forName("UTF-8");
  
  protected WxConfigProvider wxConfigProvider;
  
  /**
   * 获得access_token
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * @return
   * @throws WxErrorException 
   */
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
  
  /**
   * 发送客服消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送客服消息
   * @param message
   * @throws WxErrorException
   */
  public String sendCustomMessage(WxCustomMessage message) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    return post(url, message.toJson());
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
        StringEntity entity = new StringEntity(data, UTF8);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        resultContent = new BasicResponseHandler().handleResponse(response);
      } else if ("GET".equals(method)) {
        HttpGet httpGet = new HttpGet(uriWithAccessToken);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        resultContent = new BasicResponseHandler().handleResponse(response);
      }
      
      WxError error = WxError.fromJson(resultContent);
      /*
       * 关于微信返回错误码 详情请看 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
       * 40001 微信图片不对
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
  
  /**
   * 
   * @param menu
   * @throws WxErrorException
   */
  public String createMenu(WxMenu menu) throws WxErrorException {
    // TODO
    return null;
  }
  
  /**
   * 
   * @throws WxErrorException
   */
  public String deleteMenu() throws WxErrorException {
 // TODO
    return null;
  }

  /**
   * 
   * @return
   * @throws WxErrorException
   */
  public WxMenu getMenu() throws WxErrorException {
    // TODO
    return null;
  }
  
  public void setWxConfigProvider(WxConfigProvider wxConfigProvider) {
    this.wxConfigProvider = wxConfigProvider;
  }

}

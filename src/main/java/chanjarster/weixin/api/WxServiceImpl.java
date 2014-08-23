package chanjarster.weixin.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import chanjarster.weixin.bean.WxAccessToken;
import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.bean.result.WxError;
import chanjarster.weixin.bean.result.WxUploadResult;
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
  
  protected WxConfigStorage wxConfigStorage;
  
  public boolean checkSignature(String timestamp, String nonce, String signature) {
    try {
      String token = wxConfigStorage.getToken();
      MessageDigest sha1 = MessageDigest.getInstance("SHA1");
      String[] arr = new String[] { token, timestamp, nonce };
      Arrays.sort(arr);
      StringBuilder sb = new StringBuilder();
      for(String a : arr) {
        sb.append(a);
      }
      sha1.update(sb.toString().getBytes());
      byte[] output = sha1.digest();
      return bytesToHex(output).equals(signature);
    } catch (Exception e) {
      return false;
    }
  }
  
  protected static String bytesToHex(byte[] b) {
    char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                       '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    StringBuffer buf = new StringBuffer();
    for (int j = 0; j < b.length; j++) {
      buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
      buf.append(hexDigit[b[j] & 0x0f]);
    }
    return buf.toString();
 }
  
  public void refreshAccessToken() throws WxErrorException {
    if (!GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.getAndSet(true)) {
      try {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
            + "&appid=" + wxConfigStorage.getAppId() 
            + "&secret=" + wxConfigStorage.getSecret()
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
          wxConfigStorage.updateAccessToken(accessToken.getAccess_token(), accessToken.getExpires_in());
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

  public WxUploadResult uploadMedia(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException {
    return uploadMedia(mediaType, createTmpFile(inputStream, fileType));
  }
  
  public WxUploadResult uploadMedia(String mediaType, File file) throws WxErrorException {
    String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?type=" + mediaType;
    String json = post(url, file);
    return WxUploadResult.fromJson(json);
  }
  
  protected String post(String uri, Object data) throws WxErrorException {
    return execute("POST", uri, data);
  }
  
  protected String get(String uri, Object data) throws WxErrorException {
    return execute("GET", uri, data);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   * @param request
   * @return 微信服务端返回的结果
   * @throws WxErrorException 
   */
  protected String execute(String method, String uri, Object data) throws WxErrorException {
    if (StringUtils.isBlank(wxConfigStorage.getAccessToken())) {
      refreshAccessToken();
    }
    String accessToken = wxConfigStorage.getAccessToken();
    
    String uriWithAccessToken = uri;
    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;
    
    try {
      String resultContent = null;
      if ("POST".equals(method)) {
        HttpPost httpPost = new HttpPost(uriWithAccessToken);
        if (data != null) {
          if (data instanceof String) {
            StringEntity entity = new StringEntity((String)data, Consts.UTF_8);
            httpPost.setEntity(entity);
          }
          if (data instanceof File) {
            File file = (File) data;
            HttpEntity entity = MultipartEntityBuilder
                  .create()
                  .addBinaryBody("media", file)
                  .build();
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
          }
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);
        resultContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      } else if ("GET".equals(method)) {
        if (data != null) {
          if (data instanceof String) {
            uriWithAccessToken += uriWithAccessToken.endsWith("&") ? data : '&' + (String)data;
          }
        }
        HttpGet httpGet = new HttpGet(uriWithAccessToken);
        CloseableHttpResponse response = httpclient.execute(httpGet);
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
  
  protected File createTmpFile(InputStream inputStream, String fileType) throws IOException {
    FileOutputStream fos = null;
    try {
      File tmpFile = File.createTempFile(UUID.randomUUID().toString(), '.' + fileType);
      tmpFile.deleteOnExit();
      fos = new FileOutputStream(tmpFile);
      int read = 0;
      byte[] bytes = new byte[1024 * 100];
      while ((read = inputStream.read(bytes)) != -1) {
        fos.write(bytes, 0, read);
      }
      fos.flush();
      return tmpFile;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
        }
      }
    }
  }
  
  public void setWxConfigStorage(WxConfigStorage wxConfigProvider) {
    this.wxConfigStorage = wxConfigProvider;
  }


}

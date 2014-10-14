package me.chanjar.weixin.util.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import me.chanjar.weixin.bean.result.WxError;
import me.chanjar.weixin.exception.WxErrorException;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 * @author chanjarster
 *
 */
public class SimpleGetRequestExecutor implements RequestExecutor<String, String> {

  @Override
  public String execute(String uri, String queryParam) throws WxErrorException, ClientProtocolException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }
    HttpGet httpGet = new HttpGet(uri);
    CloseableHttpResponse response = httpclient.execute(httpGet);
    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrcode() != 0) {
      throw new WxErrorException(error);
    }
    return responseContent;
  }

}

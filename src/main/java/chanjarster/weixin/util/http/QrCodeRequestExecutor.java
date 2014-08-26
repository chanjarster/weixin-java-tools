package chanjarster.weixin.util.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;

import chanjarster.weixin.bean.result.WxError;
import chanjarster.weixin.bean.result.WxQrCodeTicket;
import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.util.fs.FileUtils;

/**
 * 获得QrCode图片 请求执行器
 * @author chanjarster
 *
 */
public class QrCodeRequestExecutor implements RequestExecutor<File, WxQrCodeTicket> {

  @Override
  public File execute(String uri, WxQrCodeTicket ticket) throws WxErrorException, ClientProtocolException, IOException {
    if (ticket != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? 
          "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8") 
          : 
          "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
    }
    
    HttpGet httpGet = new HttpGet(uri);
    CloseableHttpResponse response = httpclient.execute(httpGet);

    Header[] contentTypeHeader = response.getHeaders("Content-Type");
    if (contentTypeHeader != null && contentTypeHeader.length > 0) {
      // 出错
      if (ContentType.TEXT_PLAIN.getMimeType().equals(contentTypeHeader[0].getValue())) {
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        throw new WxErrorException(WxError.fromJson(responseContent));
      }
    }
    InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);
    
    File localFile = FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
    return localFile;
  }

}

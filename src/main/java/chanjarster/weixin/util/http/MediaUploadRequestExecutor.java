package chanjarster.weixin.util.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import chanjarster.weixin.bean.result.WxError;
import chanjarster.weixin.bean.result.WxMediaUploadResult;
import chanjarster.weixin.exception.WxErrorException;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 * @author chanjarster
 *
 */
public class MediaUploadRequestExecutor implements RequestExecutor<WxMediaUploadResult, File> {

  @Override
  public WxMediaUploadResult execute(String uri, File file) throws WxErrorException, ClientProtocolException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (file != null) {
      HttpEntity entity = MultipartEntityBuilder
            .create()
            .addBinaryBody("media", file)
            .build();
      httpPost.setEntity(entity);
      httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
    }
    CloseableHttpResponse response = httpclient.execute(httpPost);
    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrcode() != 0) {
      throw new WxErrorException(error);
    }
    return WxMediaUploadResult.fromJson(responseContent);
  }

}

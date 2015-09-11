package me.chanjar.weixin.mp.util.http;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.InputStreamResponseHandler;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MaterialVoiceAndImageDownloadRequestExecutor implements RequestExecutor<InputStream, String> {

  private File tmpDirFile;

  public MaterialVoiceAndImageDownloadRequestExecutor() {
    super();
  }

  public MaterialVoiceAndImageDownloadRequestExecutor(File tmpDirFile) {
    super();
    this.tmpDirFile = tmpDirFile;
  }

  public InputStream execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String materialId) throws WxErrorException, ClientProtocolException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    Map<String, String> params = new HashMap<>();
    params.put("media_id", materialId);
    httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
    CloseableHttpResponse response = httpclient.execute(httpPost);
    // 下载媒体文件出错
    InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);
    byte[] responseContent = IOUtils.toByteArray(inputStream);
    String responseContentString = new String(responseContent, "UTF-8");
    if (responseContentString.length() < 100) {
      try {
        WxError wxError = WxGsonBuilder.create().fromJson(responseContentString, WxError.class);
        if (wxError.getErrorCode() != 0) {
          throw new WxErrorException(wxError);
        }
      } catch (com.google.gson.JsonSyntaxException ex) {
        return new ByteArrayInputStream(responseContent);
      }
    }
    return new ByteArrayInputStream(responseContent);
  }

}

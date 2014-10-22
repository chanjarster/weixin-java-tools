package me.chanjar.weixin.common.util.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class InputStreamResponseHandler implements ResponseHandler<InputStream> {

  public static final ResponseHandler<InputStream> INSTANCE = new InputStreamResponseHandler();
  
  public InputStream handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
    final StatusLine statusLine = response.getStatusLine();
    final HttpEntity entity = response.getEntity();
    if (statusLine.getStatusCode() >= 300) {
      EntityUtils.consume(entity);
      throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
    }
    return entity == null ? null : entity.getContent();
  }

}

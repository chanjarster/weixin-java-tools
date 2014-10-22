package me.chanjar.weixin.common.util.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * http请求执行器
 * @author Daniel Qian
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 */
public interface RequestExecutor<T, E> {

  public static final CloseableHttpClient httpclient = HttpClients.createDefault();

  public T execute(String uri, E data) throws WxErrorException, ClientProtocolException, IOException;
  
}

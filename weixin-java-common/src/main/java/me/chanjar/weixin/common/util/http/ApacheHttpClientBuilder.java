package me.chanjar.weixin.common.util.http;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * httpclient build interface
 */
public interface ApacheHttpClientBuilder {

  /**
   * 构建httpclient实例
   * @return new instance of CloseableHttpClient
   */
  CloseableHttpClient build();

  /**
   * 代理服务器地址
   * @param httpProxyHost
   * @return
   */
  ApacheHttpClientBuilder httpProxyHost(String httpProxyHost);

  /**
   * 代理服务器端口
   * @param httpProxyPort
   * @return
   */
  ApacheHttpClientBuilder httpProxyPort(int httpProxyPort);

  /**
   * 代理服务器用户名
   * @param httpProxyUsername
   * @return
   */
  ApacheHttpClientBuilder httpProxyUsername(String httpProxyUsername);

  /**
   * 代理服务器密码
   * @param httpProxyPassword
   * @return
   */
  ApacheHttpClientBuilder httpProxyPassword(String httpProxyPassword);

  /**
   * ssl连接socket工厂
   * @param sslConnectionSocketFactory
   * @return
   */
  ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory);
}

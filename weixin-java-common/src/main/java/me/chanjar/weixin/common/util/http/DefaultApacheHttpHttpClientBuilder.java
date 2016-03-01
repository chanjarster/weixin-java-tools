package me.chanjar.weixin.common.util.http;

import me.chanjar.weixin.common.util.StringUtils;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * httpclient 连接管理器
 */
@NotThreadSafe
public class DefaultApacheHttpHttpClientBuilder implements ApacheHttpClientBuilder {
  private int connectionRequestTimeout = 3000;
  private int connectionTimeout = 5000;
  private int soTimeout = 5000;
  private int idleConnTimeout = 60000;
  private int checkWaitTime = 5000;
  private int maxConnPerHost = 10;
  private int maxTotalConn = 50;
  private String userAgent;
  private HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
      return false;
    }
  };
  private SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
  private PlainConnectionSocketFactory plainConnectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();

  private String httpProxyHost;
  private int httpProxyPort;
  private String httpProxyUsername;
  private String httpProxyPassword;

  /**
   * 连接管理器
   */
  private PoolingHttpClientConnectionManager connectionManager;
  /**
   * 闲置连接监控线程
   */
  private IdleConnectionMonitorThread idleConnectionMonitorThread;

  /**
   * httpClientBuilder
   */
  private HttpClientBuilder httpClientBuilder;

  private boolean prepared = false;

  private DefaultApacheHttpHttpClientBuilder() {
  }

  public static DefaultApacheHttpHttpClientBuilder get() {
    return new DefaultApacheHttpHttpClientBuilder();
  }

  public ApacheHttpClientBuilder httpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
    return this;
  }

  public ApacheHttpClientBuilder httpProxyPort(int httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
    return this;
  }

  public ApacheHttpClientBuilder httpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
    return this;
  }

  public ApacheHttpClientBuilder httpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
    return this;
  }

  public ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory){
    this.sslConnectionSocketFactory = sslConnectionSocketFactory;
    return this;
  }

  public IdleConnectionMonitorThread getIdleConnectionMonitorThread() {
    return idleConnectionMonitorThread;
  }

  private void prepare(){
    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
      .register("http", plainConnectionSocketFactory)
      .register("https", sslConnectionSocketFactory)
      .build();
    connectionManager = new PoolingHttpClientConnectionManager(registry);
    connectionManager.setMaxTotal(maxTotalConn);
    connectionManager.setDefaultMaxPerRoute(maxConnPerHost);
    connectionManager.setDefaultSocketConfig(
      SocketConfig.copy(SocketConfig.DEFAULT)
        .setSoTimeout(soTimeout)
        .build()
    );

    idleConnectionMonitorThread = new IdleConnectionMonitorThread(connectionManager, idleConnTimeout, checkWaitTime);
    idleConnectionMonitorThread.setDaemon(true);
    idleConnectionMonitorThread.start();

    httpClientBuilder = HttpClients.custom()
      .setConnectionManager(connectionManager)
      .setDefaultRequestConfig(
        RequestConfig.custom()
          .setSocketTimeout(soTimeout)
          .setConnectTimeout(connectionTimeout)
          .setConnectionRequestTimeout(connectionRequestTimeout)
          .build()
      )
      .setRetryHandler(httpRequestRetryHandler);

    if (StringUtils.isNotBlank(httpProxyHost) && StringUtils.isNotBlank(httpProxyUsername)) {
      // 使用代理服务器 需要用户认证的代理服务器
      CredentialsProvider credsProvider = new BasicCredentialsProvider();
      credsProvider.setCredentials(
        new AuthScope(httpProxyHost, httpProxyPort),
        new UsernamePasswordCredentials(httpProxyUsername, httpProxyPassword));
      httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
    }

    if (StringUtils.isNotBlank(userAgent)) {
      httpClientBuilder.setUserAgent(userAgent);
    }

  }

  public CloseableHttpClient build() {
    if(!prepared){
      prepare();
      prepared = true;
    }

    return httpClientBuilder.build();
  }

  public static class IdleConnectionMonitorThread extends Thread {
    private final HttpClientConnectionManager connMgr;
    private final int idleConnTimeout;
    private final int checkWaitTime;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr, int idleConnTimeout, int checkWaitTime) {
      super("IdleConnectionMonitorThread");
      this.connMgr = connMgr;
      this.idleConnTimeout = idleConnTimeout;
      this.checkWaitTime = checkWaitTime;
    }

    @Override
    public void run() {
      try {
        while (!shutdown) {
          synchronized (this) {
            wait(checkWaitTime);
            connMgr.closeExpiredConnections();
            connMgr.closeIdleConnections(idleConnTimeout, TimeUnit.MILLISECONDS);
          }
        }
      } catch (InterruptedException ignore) {
      }
    }

    public void trigger() {
      synchronized (this) {
        notifyAll();
      }
    }

    public void shutdown() {
      shutdown = true;
      synchronized (this) {
        notifyAll();
      }
    }
  }
}

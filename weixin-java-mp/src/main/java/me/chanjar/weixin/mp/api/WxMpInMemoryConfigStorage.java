package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.WxAccessToken;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 * @author chanjarster
 *
 */
public class WxMpInMemoryConfigStorage implements WxMpConfigStorage {

  protected String appId;
  protected String secret;
  protected String token;
  protected String accessToken;
  protected String aesKey;
  protected int expiresIn;

  protected String oauth2redirectUri;

  protected String http_proxy_host;
  protected int http_proxy_port;
  protected String http_proxy_username;
  protected String http_proxy_password;

  public void updateAccessToken(WxAccessToken accessToken) {
    updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
  }
  
  public void updateAccessToken(String accessToken, int expiresIn) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public String getAppId() {
    return this.appId;
  }

  public String getSecret() {
    return this.secret;
  }

  public String getToken() {
    return this.token;
  }

  public int getExpiresIn() {
    return this.expiresIn;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAesKey() {
    return aesKey;
  }

  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  @Override
  public String getOauth2redirectUri() {
    return this.oauth2redirectUri;
  }

  public void setOauth2redirectUri(String oauth2redirectUri) {
    this.oauth2redirectUri = oauth2redirectUri;
  }

  public String getHttp_proxy_host() {
    return http_proxy_host;
  }

  public void setHttp_proxy_host(String http_proxy_host) {
    this.http_proxy_host = http_proxy_host;
  }

  public int getHttp_proxy_port() {
    return http_proxy_port;
  }

  public void setHttp_proxy_port(int http_proxy_port) {
    this.http_proxy_port = http_proxy_port;
  }

  public String getHttp_proxy_username() {
    return http_proxy_username;
  }

  public void setHttp_proxy_username(String http_proxy_username) {
    this.http_proxy_username = http_proxy_username;
  }

  public String getHttp_proxy_password() {
    return http_proxy_password;
  }

  public void setHttp_proxy_password(String http_proxy_password) {
    this.http_proxy_password = http_proxy_password;
  }

  @Override
  public String toString() {
    return "WxMpInMemoryConfigStorage{" +
        "appId='" + appId + '\'' +
        ", secret='" + secret + '\'' +
        ", token='" + token + '\'' +
        ", accessToken='" + accessToken + '\'' +
        ", aesKey='" + aesKey + '\'' +
        ", expiresIn=" + expiresIn +
        ", http_proxy_host='" + http_proxy_host + '\'' +
        ", http_proxy_port=" + http_proxy_port +
        ", http_proxy_username='" + http_proxy_username + '\'' +
        ", http_proxy_password='" + http_proxy_password + '\'' +
        '}';
  }

}

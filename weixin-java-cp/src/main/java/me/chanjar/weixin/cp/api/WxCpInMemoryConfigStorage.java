package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.bean.WxAccessToken;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 * @author Daniel Qian
 *
 */
public class WxCpInMemoryConfigStorage implements WxCpConfigStorage {

  protected String corpId;
  protected String corpSecret;

  protected String token;
  protected String accessToken;
  protected String aesKey;
  protected String agentId;
  protected long expiresTime;

  protected String oauth2redirectUri;

  protected String http_proxy_host;
  protected int http_proxy_port;
  protected String http_proxy_username;
  protected String http_proxy_password;

  public String getAccessToken() {
    return this.accessToken;
  }

  public boolean isAccessTokenExpired() {
    return System.currentTimeMillis() > this.expiresTime;
  }

  public void expireAccessToken() {
    this.expiresTime = 0;
  }

  public synchronized void updateAccessToken(WxAccessToken accessToken) {
    updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
  }
  
  public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
    this.accessToken = accessToken;
    this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
  }

  public String getCorpId() {
    return this.corpId;
  }

  public String getCorpSecret() {
    return this.corpSecret;
  }

  public String getToken() {
    return this.token;
  }

  public long getExpiresTime() {
    return this.expiresTime;
  }

  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }

  public void setCorpSecret(String corpSecret) {
    this.corpSecret = corpSecret;
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

  public void setExpiresTime(long expiresTime) {
    this.expiresTime = expiresTime;
  }

  public String getAgentId() {
    return agentId;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
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
    return "WxCpInMemoryConfigStorage{" +
        "corpId='" + corpId + '\'' +
        ", corpSecret='" + corpSecret + '\'' +
        ", token='" + token + '\'' +
        ", accessToken='" + accessToken + '\'' +
        ", aesKey='" + aesKey + '\'' +
        ", agentId='" + agentId + '\'' +
        ", expiresTime=" + expiresTime +
        ", http_proxy_host='" + http_proxy_host + '\'' +
        ", http_proxy_port=" + http_proxy_port +
        ", http_proxy_username='" + http_proxy_username + '\'' +
        ", http_proxy_password='" + http_proxy_password + '\'' +
        '}';
  }

}

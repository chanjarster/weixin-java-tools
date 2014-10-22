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


}

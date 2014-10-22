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

  public String getCorpId() {
    return this.corpId;
  }

  public String getCorpSecret() {
    return this.corpSecret;
  }

  public String getToken() {
    return this.token;
  }

  public int getExpiresIn() {
    return this.expiresIn;
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

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getAgentId() {
    return agentId;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }

  @Override
  public String toString() {
    return "WxInMemoryCpConfigStorage{" +
        "appidOrCorpid='" + corpId + '\'' +
        ", corpSecret='" + corpSecret + '\'' +
        ", token='" + token + '\'' +
        ", accessToken='" + accessToken + '\'' +
        ", aesKey='" + aesKey + '\'' +
        ", agentId='" + agentId + '\'' +
        ", expiresIn=" + expiresIn +
        '}';
  }
}

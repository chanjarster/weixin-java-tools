package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.bean.WxAccessToken;

/**
 * 微信客户端配置存储
 * @author Daniel Qian
 *
 */
public interface WxCpConfigStorage {

  public String getAccessToken();

  public boolean isAccessTokenExpired();

  /**
   * 强制将access token过期掉
   */
  public void expireAccessToken();

  public void updateAccessToken(WxAccessToken accessToken);

  public void updateAccessToken(String accessToken, int expiresIn);


  public String getCorpId();
  
  public String getCorpSecret();

  public String getAgentId();

  public String getToken();

  public String getAesKey();

  public long getExpiresTime();

  public String getOauth2redirectUri();

  public String getHttp_proxy_host();

  public int getHttp_proxy_port();

  public String getHttp_proxy_username();

  public String getHttp_proxy_password();

}

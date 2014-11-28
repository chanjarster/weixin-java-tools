package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.bean.WxAccessToken;

/**
 * 微信客户端配置存储
 * @author Daniel Qian
 *
 */
public interface WxCpConfigStorage {

  public void updateAccessToken(WxAccessToken accessToken);
  
  public void updateAccessToken(String accessToken, int expiresIn);
  
  public String getAccessToken();
  
  public String getCorpId();
  
  public String getCorpSecret();

  public String getAgentId();

  public String getToken();

  public String getAesKey();

  public int getExpiresIn();

  public String getOauth2redirectUri();

  public String getHttp_proxy_host();

  public int getHttp_proxy_port();

  public String getHttp_proxy_username();

  public String getHttp_proxy_password();

}

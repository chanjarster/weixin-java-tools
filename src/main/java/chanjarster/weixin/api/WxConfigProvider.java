package chanjarster.weixin.api;

import chanjarster.weixin.bean.WxAccessToken;

/**
 * 微信配置的工具类
 * @author chanjarster
 *
 */
public interface WxConfigProvider {

  public void updateAccessToken(WxAccessToken accessToken);
  
  public void updateAccessToken(String accessToken, int expiresIn);
  
  public String getAccessToken();
  
  public String getAppId();
  
  public String getSecret();
  
  public String getToken();
  
  public int getExpiresIn();
  
}

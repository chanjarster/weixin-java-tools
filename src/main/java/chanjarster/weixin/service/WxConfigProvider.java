package chanjarster.weixin.service;

public interface WxConfigProvider {

  public void updateAccessToken(String accessToken, Integer expiresIn);
  
  public String getAccessToken();
  
  public String getAppId();
  
  public String getSecret();
  
  public String getToken();
  
}

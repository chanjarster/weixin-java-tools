package chanjarster.weixin.api;

import chanjarster.weixin.bean.WxAccessToken;

/**
 * 基于内存的微信配置provider
 * @author chanjarster
 *
 */
public class WxMemoryConfigProvider implements WxConfigProvider {

  protected String appId;
  protected String secret;
  protected String token;
  protected String accessToken;
  protected int expiresIn;

  public void updateAccessToken(WxAccessToken accessToken) {
    updateAccessToken(accessToken.getAccess_token(), accessToken.getExpires_in());
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

}

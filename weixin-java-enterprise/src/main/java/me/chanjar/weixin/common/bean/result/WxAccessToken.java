package me.chanjar.weixin.common.bean.result;

import me.chanjar.weixin.enterprise.util.json.WxCpGsonBuilder;

public class WxAccessToken {

  private String accessToken;
  
  private int expiresIn = -1;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  public static WxAccessToken fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxAccessToken.class);
  }
  
}

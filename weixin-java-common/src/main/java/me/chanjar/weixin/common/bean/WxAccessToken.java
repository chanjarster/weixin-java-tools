package me.chanjar.weixin.common.bean;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;

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
    return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
  }
  
}

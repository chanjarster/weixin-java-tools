package me.chanjar.weixin.enterprise.bean.result;

import me.chanjar.weixin.enterprise.util.json.WxGsonBuilder;

/**
 * 微信用户信息
 * @author Daniel Qian
 *
 */
public class WxUser {

  protected boolean subscribe;
  protected String openId;
  protected String nickname;
  protected String sex;
  protected String language;
  protected String city;
  protected String province;
  protected String country;
  protected String headImgUrl;
  protected long subscribeTime;
  protected String unionId;
  
  public boolean isSubscribe() {
    return subscribe;
  }
  public void setSubscribe(boolean subscribe) {
    this.subscribe = subscribe;
  }
  public String getOpenId() {
    return openId;
  }
  public void setOpenId(String openId) {
    this.openId = openId;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public String getSex() {
    return sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getHeadImgUrl() {
    return headImgUrl;
  }
  public void setHeadImgUrl(String headImgUrl) {
    this.headImgUrl = headImgUrl;
  }
  public long getSubscribeTime() {
    return subscribeTime;
  }
  public void setSubscribeTime(long subscribeTime) {
    this.subscribeTime = subscribeTime;
  }
  public String getUnionId() {
    return unionId;
  }
  public void setUnionId(String unionId) {
    this.unionId = unionId;
  }
  
  public static WxUser fromJson(String json) {
    return WxGsonBuilder.INSTANCE.create().fromJson(json, WxUser.class);
  }
  
}

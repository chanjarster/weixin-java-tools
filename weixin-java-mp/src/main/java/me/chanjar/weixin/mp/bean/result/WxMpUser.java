package me.chanjar.weixin.mp.bean.result;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 微信用户信息
 * @author chanjarster
 *
 */
public class WxMpUser implements Serializable {

  protected Boolean subscribe;
  protected String openId;
  protected String nickname;
  protected String sex;
  protected String language;
  protected String city;
  protected String province;
  protected String country;
  protected String headImgUrl;
  protected Long subscribeTime;
  protected String unionId;
  protected Integer sexId;
  protected String remark;
  protected Integer groupId;

  public Boolean getSubscribe() {
    return subscribe;
  }
  public Boolean isSubscribe() {
    return subscribe;
  }
  public void setSubscribe(Boolean subscribe) {
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
  public Long getSubscribeTime() {
    return subscribeTime;
  }
  public void setSubscribeTime(Long subscribeTime) {
    this.subscribeTime = subscribeTime;
  }
  public String getUnionId() {
    return unionId;
  }
  public void setUnionId(String unionId) {
    this.unionId = unionId;
  }

  public Integer getSexId() {

    return sexId;
  }

  public void setSexId(Integer sexId) {
    this.sexId = sexId;
  }

  public String getRemark() {
    return remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public Integer getGroupId() {
    return groupId;
  }
  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public static WxMpUser fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpUser.class);
  }

  @Override
  public String toString() {
    return "WxMpUser{" +
        "subscribe=" + subscribe +
        ", openId='" + openId + '\'' +
        ", nickname='" + nickname + '\'' +
        ", sex='" + sex + '\'' +
        ", language='" + language + '\'' +
        ", city='" + city + '\'' +
        ", province='" + province + '\'' +
        ", country='" + country + '\'' +
        ", headImgUrl='" + headImgUrl + '\'' +
        ", subscribeTime=" + subscribeTime +
        ", unionId='" + unionId + '\'' +
        ", remark='" + remark + '\'' +
        ", groupId='" + groupId + '\'' +
        '}';
  }
}

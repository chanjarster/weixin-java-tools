package me.chanjar.weixin.cp.bean;

import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信用户信息
 *
 * @author Daniel Qian
 */
public class WxCpUser implements Serializable {

  private String userId;
  private String name;
  private Integer[] departIds;
  private String position;
  private String mobile;
  private String gender;
  private String tel;
  private String email;
  private String weiXinId;
  private String avatar;
  private Integer status;
  private Integer enable;
  private final List<Attr> extAttrs = new ArrayList<Attr>();

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer[] getDepartIds() {
    return departIds;
  }

  public void setDepartIds(Integer[] departIds) {
    this.departIds = departIds;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getWeiXinId() {
    return weiXinId;
  }

  public void setWeiXinId(String weiXinId) {
    this.weiXinId = weiXinId;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getEnable() {
	return enable;
  }
	
  public void setEnable(Integer enable) {
	this.enable = enable;
  }
	
  public void addExtAttr(String name, String value) {
    this.extAttrs.add(new Attr(name, value));
  }

  public List<Attr> getExtAttrs() {
    return extAttrs;
  }

  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public static WxCpUser fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpUser.class);
  }

  public static class Attr {

    private String name;
    private String value;

    public Attr(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

  }

}

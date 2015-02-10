package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 微信用户分组
 * @author chanjarster
 *
 */
public class WxMpGroup implements Serializable {

  private long id = -1;
  private String name;
  private long count;
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public long getCount() {
    return count;
  }
  public void setCount(long count) {
    this.count = count;
  }
  
  public static WxMpGroup fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpGroup.class);
  }
  
  public String toJson() {
    return WxMpGsonBuilder.create().toJson(this);
  }
  @Override
  public String toString() {
    return "WxMpGroup [id=" + id + ", name=" + name + ", count=" + count + "]";
  }
  
}

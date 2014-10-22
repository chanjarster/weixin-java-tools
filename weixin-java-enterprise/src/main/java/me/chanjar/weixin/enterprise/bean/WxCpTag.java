package me.chanjar.weixin.enterprise.bean;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import me.chanjar.weixin.common.util.GsonHelper;
import me.chanjar.weixin.enterprise.util.json.WxCpGsonBuilder;

import java.io.StringReader;

/**
 * Created by Daniel Qian
 */
public class WxCpTag {

  private String id;

  private String name;

  public WxCpTag() {
    super();
  }

  public WxCpTag(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public static WxCpTag fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTag.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}

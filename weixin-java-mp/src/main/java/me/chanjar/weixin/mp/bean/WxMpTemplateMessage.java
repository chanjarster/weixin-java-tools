package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WxMpTemplateMessage implements Serializable {

  private String toUser;
  private String templateId;
  private String url;
  private String topColor;
  private List<WxMpTemplateData> datas = new ArrayList<WxMpTemplateData>();

  public String getToUser() {
    return toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTopColor() {
    return topColor;
  }

  public void setTopColor(String topColor) {
    this.topColor = topColor;
  }

  public List<WxMpTemplateData> getDatas() {
    return datas;
  }

  public void setDatas(List<WxMpTemplateData> datas) {
    this.datas = datas;
  }

  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}

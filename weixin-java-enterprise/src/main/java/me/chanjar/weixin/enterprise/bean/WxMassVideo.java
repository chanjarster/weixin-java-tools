package me.chanjar.weixin.enterprise.bean;

import me.chanjar.weixin.enterprise.util.json.WxGsonBuilder;

/**
 * 群发时用到的视频素材
 * 
 * @author Daniel Qian
 */
public class WxMassVideo {

  private String mediaId;
  private String title;
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String toJson() {
    return WxGsonBuilder.INSTANCE.create().toJson(this);
  }
}

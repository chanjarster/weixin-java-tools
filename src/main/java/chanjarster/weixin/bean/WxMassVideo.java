package chanjarster.weixin.bean;

import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 群发时用到的视频素材
 * 
 * @author chanjarster
 */
public class WxMassVideo {

  protected String media_id;
  protected String title;
  protected String description;

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

  public String getMedia_id() {
    return media_id;
  }

  public void setMedia_id(String media_id) {
    this.media_id = media_id;
  }

  public String toJson() {
    return WxGsonBuilder.INSTANCE.create().toJson(this);
  }
}

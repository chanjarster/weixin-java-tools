package chanjarster.weixin.bean.result;

import chanjarster.weixin.util.json.WxGsonBuilder;

public class WxMediaUploadResult {

  private String type;
  private String media_id;
  private String thumb_media_id;
  private long created_at;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMedia_id() {
    return media_id;
  }

  public void setMedia_id(String media_id) {
    this.media_id = media_id;
  }

  public long getCreated_at() {
    return created_at;
  }

  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }

  public static WxMediaUploadResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMediaUploadResult.class);
  }

  public String getThumb_media_id() {
    return thumb_media_id;
  }

  public void setThumb_media_id(String thumb_media_id) {
    this.thumb_media_id = thumb_media_id;
  }

  @Override
  public String toString() {
    return "WxUploadResult [type=" + type + ", media_id=" + media_id + ", thumb_media_id=" + thumb_media_id
        + ", created_at=" + created_at + "]";
  }

}

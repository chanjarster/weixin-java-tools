package chanjarster.weixin.bean.result;

import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * <pre>
 * 上传群发用的素材的结果
 * 视频和图文消息需要在群发前上传素材
 * </pre>
 * @author chanjarster
 *
 */
public class WxMassUploadResult {

  private String type;
  private String media_id;
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

  public static WxMassUploadResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMassUploadResult.class);
  }

  @Override
  public String toString() {
    return "WxUploadResult [type=" + type + ", media_id=" + media_id + ", created_at=" + created_at + "]";
  }

}

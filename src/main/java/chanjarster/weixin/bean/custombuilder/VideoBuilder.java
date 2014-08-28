package chanjarster.weixin.bean.custombuilder;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxCustomMessage;

/**
 * 视频消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE()
 *                              .media_id(...)
 *                              .title(...)
 *                              .thumb_media_id(..)
 *                              .description(..)
 *                              .touser(...)
 *                              .build();
 * </pre>
 * @author chanjarster
 *
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder> {
  private String media_id;
  private String title;
  private String description;
  private String thumb_media_id;

  public VideoBuilder() {
    this.msgtype = WxConsts.CUSTOM_MSG_VIDEO;
  }

  public VideoBuilder mediaId(String media_id) {
    this.media_id = media_id;
    return this;
  }

  public VideoBuilder title(String title) {
    this.title = title;
    return this;
  }

  public VideoBuilder description(String description) {
    this.description = description;
    return this;
  }

  public VideoBuilder thumbMediaId(String thumb_media_id) {
    this.thumb_media_id = thumb_media_id;
    return this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = super.build();
    m.setMedia_id(this.media_id);
    m.setTitle(title);
    m.setDescription(description);
    m.setThumb_media_id(thumb_media_id);
    return m;
  }
}

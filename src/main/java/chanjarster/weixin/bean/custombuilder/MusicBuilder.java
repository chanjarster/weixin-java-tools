package chanjarster.weixin.bean.custombuilder;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxCustomMessage;

/**
 * 音乐消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.MUSIC()
 *                      .musicurl(...)
 *                      .hqmusicurl(...)
 *                      .title(...)
 *                      .thumb_media_id(..)
 *                      .description(..)
 *                      .touser(...)
 *                      .build();
 * </pre>
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder> {
  private String title;
  private String description;
  private String thumb_media_id;
  private String musicurl;
  private String hqmusicurl;

  public MusicBuilder() {
    this.msgtype = WxConsts.CUSTOM_MSG_MUSIC;
  }

  public MusicBuilder musicUrl(String musicurl) {
    this.musicurl = musicurl;
    return this;
  }

  public MusicBuilder hqmusicUrl(String hqmusicurl) {
    this.hqmusicurl = hqmusicurl;
    return this;
  }

  public MusicBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MusicBuilder description(String description) {
    this.description = description;
    return this;
  }

  public MusicBuilder thumbMediaId(String thumb_media_id) {
    this.thumb_media_id = thumb_media_id;
    return this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = super.build();
    m.setMusicurl(this.musicurl);
    m.setHqmusicurl(this.hqmusicurl);
    m.setTitle(title);
    m.setDescription(description);
    m.setThumb_media_id(thumb_media_id);
    return m;
  }
}

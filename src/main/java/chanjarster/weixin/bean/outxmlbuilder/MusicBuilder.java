package chanjarster.weixin.bean.outxmlbuilder;

import chanjarster.weixin.bean.WxXmlOutMusicMessage;

/**
 * 音乐消息builder
 * 
 * @author chanjarster
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder, WxXmlOutMusicMessage> {

  private String title;
  private String description;
  private String hQMusicUrl;
  private String musicUrl;
  private String thumbMediaId;

  public MusicBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MusicBuilder description(String description) {
    this.description = description;
    return this;
  }

  public MusicBuilder hqmusicUrl(String hQMusicUrl) {
    this.hQMusicUrl = hQMusicUrl;
    return this;
  }

  public MusicBuilder musicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
    return this;
  }

  public MusicBuilder thumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
    return this;
  }

  public WxXmlOutMusicMessage build() {
    WxXmlOutMusicMessage m = new WxXmlOutMusicMessage();
    setCommon(m);
    m.setTitle(title);
    m.setDescription(description);
    m.setHQMusicUrl(hQMusicUrl);
    m.setMusicUrl(musicUrl);
    m.setThumbMediaId(thumbMediaId);
    return m;
  }

}

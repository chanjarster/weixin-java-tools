package me.chanjar.weixin.mp.bean.outxmlbuilder;

import me.chanjar.weixin.mp.bean.WxMpXmlOutMusicMessage;

/**
 * 音乐消息builder
 * 
 * @author chanjarster
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder, WxMpXmlOutMusicMessage> {

  private String title;
  private String description;
  private String hqMusicUrl;
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

  public MusicBuilder hqMusicUrl(String hqMusicUrl) {
    this.hqMusicUrl = hqMusicUrl;
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

  public WxMpXmlOutMusicMessage build() {
    WxMpXmlOutMusicMessage m = new WxMpXmlOutMusicMessage();
    setCommon(m);
    m.setTitle(title);
    m.setDescription(description);
    m.setHqMusicUrl(hqMusicUrl);
    m.setMusicUrl(musicUrl);
    m.setThumbMediaId(thumbMediaId);
    return m;
  }

}

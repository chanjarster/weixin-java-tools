package chanjarster.weixin.bean.outxmlbuilder;

import chanjarster.weixin.bean.WxXmlOutVideoMessage;

/**
 * 视频消息builder
 * @author chanjarster
 *
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxXmlOutVideoMessage> {

  private String mediaId;
  private String title;
  private String description;

  public VideoBuilder title(String title) {
    this.title = title;
    return this;
  }
  public VideoBuilder description(String description) {
    this.description = description;
    return this;
  }
  public VideoBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }
  
  public WxXmlOutVideoMessage build() {
    WxXmlOutVideoMessage m = new WxXmlOutVideoMessage();
    setCommon(m);
    m.setTitle(title);
    m.setDescription(description);
    m.setMediaId(mediaId);
    return m;
  }
  
}

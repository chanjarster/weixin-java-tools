package chanjarster.weixin.bean.outxmlbuilder;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxXmlOutImageMessage;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxXmlMessage m = WxXmlMessage.IMAGE().media_id(...).touser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxXmlOutImageMessage> {
  private String media_id;

  public ImageBuilder() {
    this.msgtype = WxConsts.XML_MSG_IMAGE;
  }

  public ImageBuilder media_id(String media_id) {
    this.media_id = media_id;
    return this;
  }

  public WxXmlOutImageMessage build() {
    WxXmlOutImageMessage m = new WxXmlOutImageMessage();
    setCommon(m);
    m.setMediaId(this.media_id);
    return m;
  }
  
}

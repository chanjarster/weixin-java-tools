package chanjarster.weixin.bean.outxmlbuilder;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxXmlOutTextMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxXmlMessage m = WxXmlMessage.TEXT().touser(...).fromUser(...).content(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxXmlOutTextMessage> {
  private String content;

  public TextBuilder() {
    this.msgtype = WxConsts.XML_MSG_TEXT;
  }

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  public WxXmlOutTextMessage build() {
    WxXmlOutTextMessage m = new WxXmlOutTextMessage();
    setCommon(m);
    m.setContent(this.content);
    return m;
  }
}

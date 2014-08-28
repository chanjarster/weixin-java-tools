package chanjarster.weixin.bean.outxmlbuilder;

import chanjarster.weixin.bean.WxXmlOutTextMessage;

/**
 * 文本消息builder
 * @author chanjarster
 *
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxXmlOutTextMessage> {
  private String content;

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

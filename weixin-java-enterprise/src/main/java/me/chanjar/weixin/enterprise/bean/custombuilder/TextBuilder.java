package me.chanjar.weixin.enterprise.bean.custombuilder;

import me.chanjar.weixin.enterprise.api.WxConsts;
import me.chanjar.weixin.enterprise.bean.WxCustomMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
  private String content;

  public TextBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_TEXT;
  }

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = super.build();
    m.setContent(this.content);
    return m;
  }
}

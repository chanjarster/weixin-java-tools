package me.chanjar.weixin.bean.custombuilder;

import me.chanjar.weixin.api.WxConsts;
import me.chanjar.weixin.bean.WxCustomMessage;

/**
 * 语音消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE().mediaId(...).touser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
  private String mediaId;

  public VoiceBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_VOICE;
  }

  public VoiceBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}

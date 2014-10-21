package me.chanjar.weixin.enterprise.bean.outxmlbuilder;

import me.chanjar.weixin.enterprise.bean.WxXmlOutVoiceMessage;

/**
 * 语音消息builder
 * @author Daniel Qian
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxXmlOutVoiceMessage> {

  private String mediaId;

  public VoiceBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }
  
  public WxXmlOutVoiceMessage build() {
    WxXmlOutVoiceMessage m = new WxXmlOutVoiceMessage();
    setCommon(m);
    m.setMediaId(mediaId);
    return m;
  }
  
}

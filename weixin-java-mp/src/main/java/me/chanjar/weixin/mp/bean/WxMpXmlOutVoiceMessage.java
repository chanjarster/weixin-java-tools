package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.MediaIdMarshaller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxMpXmlOutVoiceMessage extends WxMpXmlOutMessage {
  
  @XmlElement(name="Voice")
  @XmlJavaTypeAdapter(MediaIdMarshaller.class)
  private String mediaId;

  public WxMpXmlOutVoiceMessage() {
    this.msgType = WxConsts.XML_MSG_VOICE;
  }
  
  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  
}

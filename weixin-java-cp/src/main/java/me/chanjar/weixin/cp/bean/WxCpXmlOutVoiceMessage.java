package me.chanjar.weixin.cp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.MediaIdMarshaller;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxCpXmlOutVoiceMessage extends WxCpXmlOutMessage {
  
  @XmlElement(name="Voice")
  @XmlJavaTypeAdapter(MediaIdMarshaller.class)
  private String mediaId;

  public WxCpXmlOutVoiceMessage() {
    this.msgType = WxConsts.XML_MSG_VOICE;
  }
  
  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  
}

package me.chanjar.weixin.enterprise.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.chanjar.weixin.enterprise.api.WxCpConsts;
import me.chanjar.weixin.common.util.MediaIdMarshaller;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxCpXmlOutImageMessage extends WxCpXmlOutMessage {
  
  @XmlElement(name="Image")
  @XmlJavaTypeAdapter(MediaIdMarshaller.class)
  private String mediaId;

  public WxCpXmlOutImageMessage() {
    this.msgType = WxCpConsts.XML_MSG_IMAGE;
  }
  
  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  
}

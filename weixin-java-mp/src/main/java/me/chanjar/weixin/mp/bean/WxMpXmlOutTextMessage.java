package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.AdapterCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxMpXmlOutTextMessage extends WxMpXmlOutMessage {
  
  @XmlElement(name="Content")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String content;

  public WxMpXmlOutTextMessage() {
    this.msgType = WxConsts.XML_MSG_TEXT;
  }
  
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  
}

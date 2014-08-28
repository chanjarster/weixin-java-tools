package chanjarster.weixin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.util.xml.AdapterCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxXmlOutTextMessage extends WxXmlOutMessage {
  
  @XmlElement(name="Content")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Content;

  public WxXmlOutTextMessage() {
    this.MsgType = WxConsts.XML_MSG_TEXT;
  }
  
  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  
}

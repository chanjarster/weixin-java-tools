package chanjarster.weixin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.util.xml.MediaIdMarshaller;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxXmlOutImageMessage extends WxXmlOutMessage {
  
  @XmlElement(name="Image")
  @XmlJavaTypeAdapter(MediaIdMarshaller.class)
  private String MediaId;

  public WxXmlOutImageMessage() {
    this.MsgType = WxConsts.XML_MSG_IMAGE;
  }
  
  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }
  
}

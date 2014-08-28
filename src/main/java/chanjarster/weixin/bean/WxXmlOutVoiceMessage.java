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
public class WxXmlOutVoiceMessage extends WxXmlOutMessage {
  
  @XmlElement(name="Voice")
  @XmlJavaTypeAdapter(MediaIdMarshaller.class)
  private String MediaId;

  public WxXmlOutVoiceMessage() {
    this.MsgType = WxConsts.XML_MSG_VOICE;
  }
  
  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }
  
}

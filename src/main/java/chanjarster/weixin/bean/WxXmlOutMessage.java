package chanjarster.weixin.bean;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import chanjarster.weixin.util.xml.AdapterCDATA;
import chanjarster.weixin.util.xml.XmlTransformer;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxXmlOutMessage {

  @XmlElement(name="ToUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  protected String ToUserName;
  
  @XmlElement(name="FromUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  protected String FromUserName;
  
  @XmlElement(name="CreateTime")
  protected Long CreateTime;
  
  @XmlElement(name="MsgType")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  protected String MsgType;

  public String getToUserName() {
    return ToUserName;
  }

  public void setToUserName(String toUserName) {
    ToUserName = toUserName;
  }

  public String getFromUserName() {
    return FromUserName;
  }

  public void setFromUserName(String fromUserName) {
    FromUserName = fromUserName;
  }

  public Long getCreateTime() {
    return CreateTime;
  }

  public void setCreateTime(Long createTime) {
    CreateTime = createTime;
  }

  public String getMsgType() {
    return MsgType;
  }

  public void setMsgType(String msgType) {
    MsgType = msgType;
  }
  
  public String toXml() {
    try {
      return XmlTransformer.toXml((Class)this.getClass(), this);
    } catch (JAXBException e) {
    }
    return null;
  }
  
}

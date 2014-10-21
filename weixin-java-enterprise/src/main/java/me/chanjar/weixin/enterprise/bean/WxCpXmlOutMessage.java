package me.chanjar.weixin.enterprise.bean;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.chanjar.weixin.enterprise.api.WxCpConfigStorage;
import me.chanjar.weixin.enterprise.bean.outxmlbuilder.ImageBuilder;
import me.chanjar.weixin.enterprise.bean.outxmlbuilder.NewsBuilder;
import me.chanjar.weixin.enterprise.bean.outxmlbuilder.TextBuilder;
import me.chanjar.weixin.enterprise.bean.outxmlbuilder.VideoBuilder;
import me.chanjar.weixin.enterprise.bean.outxmlbuilder.VoiceBuilder;
import me.chanjar.weixin.enterprise.util.crypto.WxCryptUtil;
import me.chanjar.weixin.common.AdapterCDATA;
import me.chanjar.weixin.common.XmlTransformer;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxCpXmlOutMessage {

  @XmlElement(name="ToUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  protected String toUserName;
  
  @XmlElement(name="FromUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  protected String fromUserName;
  
  @XmlElement(name="CreateTime")
  protected Long createTime;
  
  @XmlElement(name="MsgType")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  protected String msgType;

  public String getToUserName() {
    return toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public String getFromUserName() {
    return fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public String getMsgType() {
    return msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }
  
  protected String toXml() {
    try {
      return XmlTransformer.toXml((Class)this.getClass(), this);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 转换成加密的xml格式
   * @return
   */
  public String toEncryptedXml(WxCpConfigStorage wxCpConfigStorage) {
    String plainXml = toXml();
    WxCryptUtil pc = new WxCryptUtil(wxCpConfigStorage);
    return pc.encrypt(plainXml);
  }

  /**
   * 获得文本消息builder
   * @return
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  /**
   * 获得图片消息builder
   * @return
   */
  public static ImageBuilder IMAGE() {
    return new ImageBuilder();
  }

  /**
   * 获得语音消息builder
   * @return
   */
  public static VoiceBuilder VOICE() {
    return new VoiceBuilder();
  }
  
  /**
   * 获得视频消息builder
   * @return
   */
  public static VideoBuilder VIDEO() {
    return new VideoBuilder();
  }
  
  /**
   * 获得图文消息builder
   * @return
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }
}

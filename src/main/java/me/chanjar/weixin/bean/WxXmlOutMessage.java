package me.chanjar.weixin.bean;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.chanjar.weixin.api.WxConfigStorage;
import me.chanjar.weixin.bean.outxmlbuilder.ImageBuilder;
import me.chanjar.weixin.bean.outxmlbuilder.MusicBuilder;
import me.chanjar.weixin.bean.outxmlbuilder.NewsBuilder;
import me.chanjar.weixin.bean.outxmlbuilder.TextBuilder;
import me.chanjar.weixin.bean.outxmlbuilder.VideoBuilder;
import me.chanjar.weixin.bean.outxmlbuilder.VoiceBuilder;
import me.chanjar.weixin.util.crypto.WxCryptUtil;
import me.chanjar.weixin.util.xml.AdapterCDATA;
import me.chanjar.weixin.util.xml.XmlTransformer;

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
      throw new RuntimeException(e);
    }
  }

  /**
   * 转换成加密的xml格式
   * @return
   */
  public String toEncryptedXml(WxConfigStorage wxConfigStorage) {
    String plainXml = toXml();
    WxCryptUtil pc = new WxCryptUtil(wxConfigStorage);
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
   * 获得音乐消息builder
   * @return
   */
  public static MusicBuilder MUSIC() {
    return new MusicBuilder();
  }
  
  /**
   * 获得图文消息builder
   * @return
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }
}

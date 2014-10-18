package me.chanjar.weixin.bean;

import me.chanjar.weixin.api.WxConfigStorage;
import me.chanjar.weixin.api.WxConsts;
import me.chanjar.weixin.util.crypto.WxCryptUtil;
import me.chanjar.weixin.util.xml.AdapterCDATA;
import me.chanjar.weixin.util.xml.XmlTransformer;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 *
 * @author chanjarster
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxXmlMessage {

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////

  @XmlElement(name = "ToUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String ToUserName;

  @XmlElement(name = "FromUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String FromUserName;

  @XmlElement(name = "CreateTime")
  private Long CreateTime;

  @XmlElement(name = "MsgType")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String MsgType;

  @XmlElement(name = "Content")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Content;

  @XmlElement(name = "MsgId")
  private Long MsgId;

  @XmlElement(name = "PicUrl")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String PicUrl;

  @XmlElement(name = "MediaId")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String MediaId;

  @XmlElement(name = "Format")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Format;

  @XmlElement(name = "ThumbMediaId")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String ThumbMediaId;

  @XmlElement(name = "Location_X")
  private Double Location_X;

  @XmlElement(name = "Location_Y")
  private Double Location_Y;

  @XmlElement(name = "Scale")
  private Double Scale;

  @XmlElement(name = "Label")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Label;

  @XmlElement(name = "Title")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Title;

  @XmlElement(name = "Description")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Description;

  @XmlElement(name = "Url")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Url;

  @XmlElement(name = "Event")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Event;

  @XmlElement(name = "EventKey")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String EventKey;

  @XmlElement(name = "Ticket")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Ticket;

  @XmlElement(name = "Latitude")
  private Double Latitude;

  @XmlElement(name = "Longitude")
  private Double Longitude;

  @XmlElement(name = "Precision")
  private Double Precision;

  @XmlElement(name = "Recognition")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Recognition;

  ///////////////////////////////////////
  // 群发消息返回的结果
  ///////////////////////////////////////
  /**
   * 群发的结果
   */
  @XmlElement(name = "Status")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Status;
  /**
   * group_id下粉丝数；或者openid_list中的粉丝数
   */
  private Integer TotalCount;
  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount
   */
  private Integer FilterCount;
  /**
   * 发送成功的粉丝数
   */
  private Integer SentCount;
  /**
   * 发送失败的粉丝数
   */
  private Integer ErrorCount;

  public String getToUserName() {
    return ToUserName;
  }

  public void setToUserName(String toUserName) {
    ToUserName = toUserName;
  }

  public Long getCreateTime() {
    return CreateTime;
  }

  public void setCreateTime(Long createTime) {
    CreateTime = createTime;
  }

  /**
   * <pre>
   * 当接受用户消息时，可能会获得以下值：
   * {@link WxConsts#XML_MSG_TEXT}
   * {@link WxConsts#XML_MSG_IMAGE}
   * {@link WxConsts#XML_MSG_VOICE}
   * {@link WxConsts#XML_MSG_VIDEO}
   * {@link WxConsts#XML_MSG_LOCATION}
   * {@link WxConsts#XML_MSG_LINK}
   * {@link WxConsts#XML_MSG_EVENT}
   * </pre>
   *
   * @return
   */
  public String getMsgType() {
    return MsgType;
  }

  /**
   * <pre>
   * 当发送消息的时候使用：
   * {@link WxConsts#XML_MSG_TEXT}
   * {@link WxConsts#XML_MSG_IMAGE}
   * {@link WxConsts#XML_MSG_VOICE}
   * {@link WxConsts#XML_MSG_VIDEO}
   * {@link WxConsts#XML_MSG_NEWS}
   * {@link WxConsts#XML_MSG_MUSIC}
   * </pre>
   *
   * @param msgType
   */
  public void setMsgType(String msgType) {
    MsgType = msgType;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  public Long getMsgId() {
    return MsgId;
  }

  public void setMsgId(Long msgId) {
    MsgId = msgId;
  }

  public String getPicUrl() {
    return PicUrl;
  }

  public void setPicUrl(String picUrl) {
    PicUrl = picUrl;
  }

  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }

  public String getFormat() {
    return Format;
  }

  public void setFormat(String format) {
    Format = format;
  }

  public String getThumbMediaId() {
    return ThumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId) {
    ThumbMediaId = thumbMediaId;
  }

  public Double getLocation_X() {
    return Location_X;
  }

  public void setLocation_X(Double location_X) {
    Location_X = location_X;
  }

  public Double getLocation_Y() {
    return Location_Y;
  }

  public void setLocation_Y(Double location_Y) {
    Location_Y = location_Y;
  }

  public Double getScale() {
    return Scale;
  }

  public void setScale(Double scale) {
    Scale = scale;
  }

  public String getLabel() {
    return Label;
  }

  public void setLabel(String label) {
    Label = label;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public String getUrl() {
    return Url;
  }

  public void setUrl(String url) {
    Url = url;
  }

  public String getEvent() {
    return Event;
  }

  public void setEvent(String event) {
    Event = event;
  }

  public String getEventKey() {
    return EventKey;
  }

  public void setEventKey(String eventKey) {
    EventKey = eventKey;
  }

  public String getTicket() {
    return Ticket;
  }

  public void setTicket(String ticket) {
    Ticket = ticket;
  }

  public Double getLatitude() {
    return Latitude;
  }

  public void setLatitude(Double latitude) {
    Latitude = latitude;
  }

  public Double getLongitude() {
    return Longitude;
  }

  public void setLongitude(Double longitude) {
    Longitude = longitude;
  }

  public Double getPrecision() {
    return Precision;
  }

  public void setPrecision(Double precision) {
    Precision = precision;
  }

  public String getRecognition() {
    return Recognition;
  }

  public void setRecognition(String recognition) {
    Recognition = recognition;
  }

  public String getFromUserName() {
    return FromUserName;
  }

  public void setFromUserName(String fromUserName) {
    FromUserName = fromUserName;
  }

  public static WxXmlMessage fromXml(String xml) {
    try {
      return XmlTransformer.fromXml(WxXmlMessage.class, xml);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  public static WxXmlMessage fromXml(InputStream is) {
    try {
      return XmlTransformer.fromXml(WxXmlMessage.class, is);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml
   * @param wxConfigStorage
   * @param timestamp
   * @param nonce
   * @param msgSignature
   * @return
   */
  public static WxXmlMessage fromEncryptedXml(
      String encryptedXml,
      WxConfigStorage wxConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    WxCryptUtil cryptUtil = new WxCryptUtil(wxConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return fromXml(plainText);
  }

  public static WxXmlMessage fromEncryptedXml(
      InputStream is,
      WxConfigStorage wxConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxConfigStorage, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public Integer getTotalCount() {
    return TotalCount;
  }

  public void setTotalCount(Integer totalCount) {
    TotalCount = totalCount;
  }

  public Integer getFilterCount() {
    return FilterCount;
  }

  public void setFilterCount(Integer filterCount) {
    FilterCount = filterCount;
  }

  public Integer getSentCount() {
    return SentCount;
  }

  public void setSentCount(Integer sentCount) {
    SentCount = sentCount;
  }

  public Integer getErrorCount() {
    return ErrorCount;
  }

  public void setErrorCount(Integer errorCount) {
    ErrorCount = errorCount;
  }

}

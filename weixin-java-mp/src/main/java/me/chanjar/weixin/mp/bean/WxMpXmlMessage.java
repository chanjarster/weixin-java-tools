package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.util.xml.AdapterCDATA;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.util.crypto.WxMpCryptUtil;
import me.chanjar.weixin.mp.util.xml.XmlTransformer;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
public class WxMpXmlMessage {

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////

  @XmlElement(name = "ToUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String toUserName;

  @XmlElement(name = "FromUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String fromUserName;

  @XmlElement(name = "CreateTime")
  private Long createTime;

  @XmlElement(name = "MsgType")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String msgType;

  @XmlElement(name = "Content")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String content;

  @XmlElement(name = "MsgId")
  private Long msgId;

  @XmlElement(name = "PicUrl")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String picUrl;

  @XmlElement(name = "MediaId")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String mediaId;

  @XmlElement(name = "Format")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String format;

  @XmlElement(name = "ThumbMediaId")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String thumbMediaId;

  @XmlElement(name = "Location_X")
  private Double locationX;

  @XmlElement(name = "Location_Y")
  private Double locationY;

  @XmlElement(name = "Scale")
  private Double scale;

  @XmlElement(name = "Label")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String label;

  @XmlElement(name = "Title")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String title;

  @XmlElement(name = "Description")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String description;

  @XmlElement(name = "Url")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String url;

  @XmlElement(name = "Event")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String event;

  @XmlElement(name = "EventKey")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String eventKey;

  @XmlElement(name = "Ticket")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String ticket;

  @XmlElement(name = "Latitude")
  private Double latitude;

  @XmlElement(name = "Longitude")
  private Double longitude;

  @XmlElement(name = "Precision")
  private Double precision;

  @XmlElement(name = "Recognition")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String recognition;

  ///////////////////////////////////////
  // 群发消息返回的结果
  ///////////////////////////////////////
  /**
   * 群发的结果
   */
  @XmlElement(name = "Status")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String status;
  /**
   * group_id下粉丝数；或者openid_list中的粉丝数
   */
  @XmlElement(name = "TotalCount")
  private Integer totalCount;
  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，filterCount = sentCount + errorCount
   */
  @XmlElement(name = "FilterCount")
  private Integer filterCount;
  /**
   * 发送成功的粉丝数
   */
  @XmlElement(name = "SentCount")
  private Integer sentCount;
  /**
   * 发送失败的粉丝数
   */
  @XmlElement(name = "ErrorCount")
  private Integer errorCount;

  @XmlElement(name = "ScanCodeInfo")
  private ScanCodeInfo scanCodeInfo = new ScanCodeInfo();

  @XmlElement(name = "SendPicsInfo")
  private SendPicsInfo sendPicsInfo = new SendPicsInfo();

  @XmlElement(name = "SendLocationInfo")
  private SendLocationInfo sendLocationInfo = new SendLocationInfo();

  public String getToUserName() {
    return toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  /**
   * <pre>
   * 当接受用户消息时，可能会获得以下值：
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LOCATION}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LINK}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_EVENT}
   * </pre>
   *
   * @return
   */
  public String getMsgType() {
    return msgType;
  }

  /**
   * <pre>
   * 当发送消息的时候使用：
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_NEWS}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_MUSIC}
   * </pre>
   *
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getMsgId() {
    return msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getThumbMediaId() {
    return thumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }

  public Double getLocationX() {
    return locationX;
  }

  public void setLocationX(Double locationX) {
    this.locationX = locationX;
  }

  public Double getLocationY() {
    return locationY;
  }

  public void setLocationY(Double locationY) {
    this.locationY = locationY;
  }

  public Double getScale() {
    return scale;
  }

  public void setScale(Double scale) {
    this.scale = scale;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getPrecision() {
    return precision;
  }

  public void setPrecision(Double precision) {
    this.precision = precision;
  }

  public String getRecognition() {
    return recognition;
  }

  public void setRecognition(String recognition) {
    this.recognition = recognition;
  }

  public String getFromUserName() {
    return fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public static WxMpXmlMessage fromXml(String xml) {
    try {
      return XmlTransformer.fromXml(WxMpXmlMessage.class, xml);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  public static WxMpXmlMessage fromXml(InputStream is) {
    try {
      return XmlTransformer.fromXml(WxMpXmlMessage.class, is);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml
   * @param wxMpConfigStorage
   * @param timestamp
   * @param nonce
   * @param msgSignature
   * @return
   */
  public static WxMpXmlMessage fromEncryptedXml(
      String encryptedXml,
      WxMpConfigStorage wxMpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    WxMpCryptUtil cryptUtil = new WxMpCryptUtil(wxMpConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return fromXml(plainText);
  }

  public static WxMpXmlMessage fromEncryptedXml(
      InputStream is,
      WxMpConfigStorage wxMpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxMpConfigStorage, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getFilterCount() {
    return filterCount;
  }

  public void setFilterCount(Integer filterCount) {
    this.filterCount = filterCount;
  }

  public Integer getSentCount() {
    return sentCount;
  }

  public void setSentCount(Integer sentCount) {
    this.sentCount = sentCount;
  }

  public Integer getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(Integer errorCount) {
    this.errorCount = errorCount;
  }

  public WxMpXmlMessage.ScanCodeInfo getScanCodeInfo() {
    return scanCodeInfo;
  }

  public void setScanCodeInfo(WxMpXmlMessage.ScanCodeInfo scanCodeInfo) {
    this.scanCodeInfo = scanCodeInfo;
  }

  public WxMpXmlMessage.SendPicsInfo getSendPicsInfo() {
    return sendPicsInfo;
  }

  public void setSendPicsInfo(WxMpXmlMessage.SendPicsInfo sendPicsInfo) {
    this.sendPicsInfo = sendPicsInfo;
  }

  public WxMpXmlMessage.SendLocationInfo getSendLocationInfo() {
    return sendLocationInfo;
  }

  public void setSendLocationInfo(WxMpXmlMessage.SendLocationInfo sendLocationInfo) {
    this.sendLocationInfo = sendLocationInfo;
  }

  @XmlRootElement(name = "ScanCodeInfo")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class ScanCodeInfo {

    @XmlElement(name = "ScanType")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scanType;

    @XmlElement(name = "ScanResult")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scanResult;

    /**
     * 扫描类型，一般是qrcode
     * @return
     */
    public String getScanType() {

      return scanType;
    }

    public void setScanType(String scanType) {
      this.scanType = scanType;
    }

    /**
     * 扫描结果，即二维码对应的字符串信息
     * @return
     */
    public String getScanResult() {
      return scanResult;
    }

    public void setScanResult(String scanResult) {
      this.scanResult = scanResult;
    }

  }

  @XmlRootElement(name = "SendPicsInfo")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class SendPicsInfo {

    @XmlElement(name = "Count")
    private Long count;

    @XmlElementWrapper(name="PicList")
    @XmlElement(name = "item")
    protected final List<Item> picList = new ArrayList<Item>();

    public Long getCount() {
      return count;
    }

    public void setCount(Long count) {
      this.count = count;
    }

    public List<Item> getPicList() {
      return picList;
    }

    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "WxXmlMessage.SendPicsInfo.Item")
    public static class Item {

      @XmlElement(name = "PicMd5Sum")
      @XmlJavaTypeAdapter(AdapterCDATA.class)
      private String PicMd5Sum;

      public String getPicMd5Sum() {
        return PicMd5Sum;
      }

      public void setPicMd5Sum(String picMd5Sum) {
        PicMd5Sum = picMd5Sum;
      }
    }
  }

  @XmlRootElement(name = "SendLocationInfo")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class SendLocationInfo {

    @XmlElement(name = "Location_X")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String locationX;

    @XmlElement(name = "Location_Y")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String locationY;

    @XmlElement(name = "Scale")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scale;

    @XmlElement(name = "Label")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String label;

    @XmlElement(name = "Poiname")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String poiname;

    public String getLocationX() {
      return locationX;
    }

    public void setLocationX(String locationX) {
      this.locationX = locationX;
    }

    public String getLocationY() {
      return locationY;
    }

    public void setLocationY(String locationY) {
      this.locationY = locationY;
    }

    public String getScale() {
      return scale;
    }

    public void setScale(String scale) {
      this.scale = scale;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getPoiname() {
      return poiname;
    }

    public void setPoiname(String poiname) {
      this.poiname = poiname;
    }
  }

}

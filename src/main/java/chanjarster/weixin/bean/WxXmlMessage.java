package chanjarster.weixin.bean;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import chanjarster.weixin.util.AdapterCDATA;
import chanjarster.weixin.util.XmlTransformer;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 * @author chanjarster
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxXmlMessage {

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////
  
  @XmlElement(name="ToUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String ToUserName;
  
  @XmlElement(name="FromUserName")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String FromUserName;
  
  @XmlElement(name="CreateTime")
  private Long CreateTime;
  
  @XmlElement(name="MsgType")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String MsgType;
  
  @XmlElement(name="Content")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Content;

  @XmlElement(name="MsgId")
  private Long MsgId;
  
  @XmlElement(name="PicUrl")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String PicUrl;
  
  @XmlElement(name="MediaId")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String MediaId;
  
  @XmlElement(name="Format")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Format;
  
  @XmlElement(name="ThumbMediaId")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String ThumbMediaId;
  
  @XmlElement(name="Location_X")
  private Double Location_X;
  
  @XmlElement(name="Location_Y")
  private Double Location_Y;
  
  @XmlElement(name="Scale")
  private Double Scale;
  
  @XmlElement(name="Label")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Label;
  
  @XmlElement(name="Title")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Title;
  
  @XmlElement(name="Description")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Description;
  
  @XmlElement(name="Url")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Url;
  
  @XmlElement(name="Event")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Event;
  
  @XmlElement(name="EventKey")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String EventKey;
  
  @XmlElement(name="Ticket")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Ticket;
  
  @XmlElement(name="Latitude")
  private Double Latitude;
  
  @XmlElement(name="Longitude")
  private Double Longitude;
  
  @XmlElement(name="Precision")
  private Double Precision;
  
  @XmlElement(name="Recognition")
  @XmlJavaTypeAdapter(AdapterCDATA.class)
  private String Recognition;
  
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
  
  public String getMsgType() {
    return MsgType;
  }
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
  
  public String toXml() {
    try {
      return XmlTransformer.toXml(WxXmlMessage.class, this);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return "";
  }
  
  public static WxXmlMessage fromXml(String xml) {
    try {
      return XmlTransformer.fromXml(WxXmlMessage.class, xml);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static WxXmlMessage fromXml(InputStream is) {
    try {
      return XmlTransformer.fromXml(WxXmlMessage.class, is);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((Content == null) ? 0 : Content.hashCode());
    result = prime * result + ((CreateTime == null) ? 0 : CreateTime.hashCode());
    result = prime * result + ((Description == null) ? 0 : Description.hashCode());
    result = prime * result + ((Event == null) ? 0 : Event.hashCode());
    result = prime * result + ((EventKey == null) ? 0 : EventKey.hashCode());
    result = prime * result + ((Format == null) ? 0 : Format.hashCode());
    result = prime * result + ((FromUserName == null) ? 0 : FromUserName.hashCode());
    result = prime * result + ((Label == null) ? 0 : Label.hashCode());
    result = prime * result + ((Latitude == null) ? 0 : Latitude.hashCode());
    result = prime * result + ((Location_X == null) ? 0 : Location_X.hashCode());
    result = prime * result + ((Location_Y == null) ? 0 : Location_Y.hashCode());
    result = prime * result + ((Longitude == null) ? 0 : Longitude.hashCode());
    result = prime * result + ((MediaId == null) ? 0 : MediaId.hashCode());
    result = prime * result + ((MsgId == null) ? 0 : MsgId.hashCode());
    result = prime * result + ((MsgType == null) ? 0 : MsgType.hashCode());
    result = prime * result + ((PicUrl == null) ? 0 : PicUrl.hashCode());
    result = prime * result + ((Precision == null) ? 0 : Precision.hashCode());
    result = prime * result + ((Recognition == null) ? 0 : Recognition.hashCode());
    result = prime * result + ((Scale == null) ? 0 : Scale.hashCode());
    result = prime * result + ((ThumbMediaId == null) ? 0 : ThumbMediaId.hashCode());
    result = prime * result + ((Ticket == null) ? 0 : Ticket.hashCode());
    result = prime * result + ((Title == null) ? 0 : Title.hashCode());
    result = prime * result + ((ToUserName == null) ? 0 : ToUserName.hashCode());
    result = prime * result + ((Url == null) ? 0 : Url.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    WxXmlMessage other = (WxXmlMessage) obj;
    if (Content == null) {
      if (other.Content != null) return false;
    } else if (!Content.equals(other.Content)) return false;
    if (CreateTime == null) {
      if (other.CreateTime != null) return false;
    } else if (!CreateTime.equals(other.CreateTime)) return false;
    if (Description == null) {
      if (other.Description != null) return false;
    } else if (!Description.equals(other.Description)) return false;
    if (Event == null) {
      if (other.Event != null) return false;
    } else if (!Event.equals(other.Event)) return false;
    if (EventKey == null) {
      if (other.EventKey != null) return false;
    } else if (!EventKey.equals(other.EventKey)) return false;
    if (Format == null) {
      if (other.Format != null) return false;
    } else if (!Format.equals(other.Format)) return false;
    if (FromUserName == null) {
      if (other.FromUserName != null) return false;
    } else if (!FromUserName.equals(other.FromUserName)) return false;
    if (Label == null) {
      if (other.Label != null) return false;
    } else if (!Label.equals(other.Label)) return false;
    if (Latitude == null) {
      if (other.Latitude != null) return false;
    } else if (!Latitude.equals(other.Latitude)) return false;
    if (Location_X == null) {
      if (other.Location_X != null) return false;
    } else if (!Location_X.equals(other.Location_X)) return false;
    if (Location_Y == null) {
      if (other.Location_Y != null) return false;
    } else if (!Location_Y.equals(other.Location_Y)) return false;
    if (Longitude == null) {
      if (other.Longitude != null) return false;
    } else if (!Longitude.equals(other.Longitude)) return false;
    if (MediaId == null) {
      if (other.MediaId != null) return false;
    } else if (!MediaId.equals(other.MediaId)) return false;
    if (MsgId == null) {
      if (other.MsgId != null) return false;
    } else if (!MsgId.equals(other.MsgId)) return false;
    if (MsgType == null) {
      if (other.MsgType != null) return false;
    } else if (!MsgType.equals(other.MsgType)) return false;
    if (PicUrl == null) {
      if (other.PicUrl != null) return false;
    } else if (!PicUrl.equals(other.PicUrl)) return false;
    if (Precision == null) {
      if (other.Precision != null) return false;
    } else if (!Precision.equals(other.Precision)) return false;
    if (Recognition == null) {
      if (other.Recognition != null) return false;
    } else if (!Recognition.equals(other.Recognition)) return false;
    if (Scale == null) {
      if (other.Scale != null) return false;
    } else if (!Scale.equals(other.Scale)) return false;
    if (ThumbMediaId == null) {
      if (other.ThumbMediaId != null) return false;
    } else if (!ThumbMediaId.equals(other.ThumbMediaId)) return false;
    if (Ticket == null) {
      if (other.Ticket != null) return false;
    } else if (!Ticket.equals(other.Ticket)) return false;
    if (Title == null) {
      if (other.Title != null) return false;
    } else if (!Title.equals(other.Title)) return false;
    if (ToUserName == null) {
      if (other.ToUserName != null) return false;
    } else if (!ToUserName.equals(other.ToUserName)) return false;
    if (Url == null) {
      if (other.Url != null) return false;
    } else if (!Url.equals(other.Url)) return false;
    return true;
  }

  
}
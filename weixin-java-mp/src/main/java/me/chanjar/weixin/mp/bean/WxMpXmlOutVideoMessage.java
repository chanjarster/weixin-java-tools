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
public class WxMpXmlOutVideoMessage extends WxMpXmlOutMessage {

  @XmlElement(name = "Video")
  protected final Video video = new Video();

  public WxMpXmlOutVideoMessage() {
    this.msgType = WxConsts.XML_MSG_VIDEO;
  }

  public String getMediaId() {
    return video.getMediaId();
  }

  public void setMediaId(String mediaId) {
    video.setMediaId(mediaId);
  }

  public String getTitle() {
    return video.getTitle();
  }

  public void setTitle(String title) {
    video.setTitle(title);
  }

  public String getDescription() {
    return video.getDescription();
  }

  public void setDescription(String description) {
    video.setDescription(description);
  }
  

  @XmlRootElement(name = "Video")
  @XmlAccessorType(XmlAccessType.FIELD)
  private static class Video {
    
    @XmlElement(name = "MediaId")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String mediaId;

    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String title;

    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String description;

    public String getMediaId() {
      return mediaId;
    }

    public void setMediaId(String mediaId) {
      this.mediaId = mediaId;
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
    
  }

}

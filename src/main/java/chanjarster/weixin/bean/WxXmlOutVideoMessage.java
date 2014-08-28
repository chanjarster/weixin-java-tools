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
public class WxXmlOutVideoMessage extends WxXmlOutMessage {

  @XmlElement(name = "Video")
  protected final Video video = new Video();

  public WxXmlOutVideoMessage() {
    this.MsgType = WxConsts.XML_MSG_VIDEO;
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
    private String MediaId;

    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Title;

    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Description;

    public String getMediaId() {
      return MediaId;
    }

    public void setMediaId(String mediaId) {
      MediaId = mediaId;
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
    
  }

}

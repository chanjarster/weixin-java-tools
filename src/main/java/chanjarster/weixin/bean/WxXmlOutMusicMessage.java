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
public class WxXmlOutMusicMessage extends WxXmlOutMessage {

  @XmlElement(name = "Music")
  protected final Music music = new Music();

  public WxXmlOutMusicMessage() {
    this.MsgType = WxConsts.XML_MSG_MUSIC;
  }

  public String getTitle() {
    return music.getTitle();
  }

  public void setTitle(String title) {
    music.setTitle(title);
  }

  public String getDescription() {
    return music.getDescription();
  }

  public void setDescription(String description) {
    music.setDescription(description);
  }
  
  public String getThumbMediaId() {
    return music.getThumbMediaId();
  }

  public void setThumbMediaId(String thumbMediaId) {
    music.setThumbMediaId(thumbMediaId);
  }

  public String getMusicUrl() {
    return music.getMusicUrl();
  }

  public void setMusicUrl(String musicUrl) {
    music.setMusicUrl(musicUrl);
  }

  public String getHQMusicUrl() {
    return music.getHQMusicUrl();
  }

  public void setHQMusicUrl(String hQMusicUrl) {
    music.setHQMusicUrl(hQMusicUrl);
  }
  
  @XmlRootElement(name = "Music")
  @XmlAccessorType(XmlAccessType.FIELD)
  private static class Music {
    
    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Title;

    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Description;

    @XmlElement(name="ThumbMediaId")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String ThumbMediaId;
    
    @XmlElement(name="MusicUrl")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String MusicUrl;
    
    @XmlElement(name="HQMusicUrl")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String HQMusicUrl;
    
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

    public String getThumbMediaId() {
      return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
      ThumbMediaId = thumbMediaId;
    }

    public String getMusicUrl() {
      return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
      MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
      return HQMusicUrl;
    }

    public void setHQMusicUrl(String hQMusicUrl) {
      HQMusicUrl = hQMusicUrl;
    }
    
  }

}

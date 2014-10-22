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
public class WxMpXmlOutMusicMessage extends WxMpXmlOutMessage {

  @XmlElement(name = "Music")
  protected final Music music = new Music();

  public WxMpXmlOutMusicMessage() {
    this.msgType = WxConsts.XML_MSG_MUSIC;
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

  public String getHqMusicUrl() {
    return music.getHqMusicUrl();
  }

  public void setHqMusicUrl(String hqMusicUrl) {
    music.setHqMusicUrl(hqMusicUrl);
  }
  
  @XmlRootElement(name = "Music")
  @XmlAccessorType(XmlAccessType.FIELD)
  private static class Music {
    
    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String title;

    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String description;

    @XmlElement(name="ThumbMediaId")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String thumbMediaId;
    
    @XmlElement(name="MusicUrl")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String musicUrl;
    
    @XmlElement(name="HQMusicUrl")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String hqMusicUrl;
    
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

    public String getThumbMediaId() {
      return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
      this.thumbMediaId = thumbMediaId;
    }

    public String getMusicUrl() {
      return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
      this.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
      return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
      this.hqMusicUrl = hqMusicUrl;
    }
    
  }

}

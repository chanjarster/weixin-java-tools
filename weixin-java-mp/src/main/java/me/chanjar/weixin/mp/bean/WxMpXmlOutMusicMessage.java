package me.chanjar.weixin.mp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
public class WxMpXmlOutMusicMessage extends WxMpXmlOutMessage {

  @XStreamAlias("Music")
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
  
  @XStreamAlias("Music")
  public static class Music {
    
    @XStreamAlias("Title")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String title;

    @XStreamAlias("Description")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String description;

    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String thumbMediaId;
    
    @XStreamAlias("MusicUrl")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String musicUrl;
    
    @XStreamAlias("HQMusicUrl")
    @XStreamConverter(value=XStreamCDataConverter.class)
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

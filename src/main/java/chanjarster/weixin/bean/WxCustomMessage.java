package chanjarster.weixin.bean;

import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.custombuilder.ImageBuilder;
import chanjarster.weixin.bean.custombuilder.MusicBuilder;
import chanjarster.weixin.bean.custombuilder.NewsBuilder;
import chanjarster.weixin.bean.custombuilder.TextBuilder;
import chanjarster.weixin.bean.custombuilder.VideoBuilder;
import chanjarster.weixin.bean.custombuilder.VoiceBuilder;
import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 客服消息
 * @author chanjarster
 *
 */
public class WxCustomMessage {

  private String touser;
  private String msgtype;
  private String content;
  private String media_id;
  private String thumb_media_id;
  private String title;
  private String description;
  private String musicurl;
  private String hqmusicurl;
  private List<WxArticle> articles = new ArrayList<WxArticle>();
  
  public String getTouser() {
    return touser;
  }
  public void setTouser(String touser) {
    this.touser = touser;
  }
  public String getMsgtype() {
    return msgtype;
  }
  
  /**
   * <pre>
   * 请使用
   * {@link WxConsts#CUSTOM_MSG_TEXT}
   * {@link WxConsts#CUSTOM_MSG_IMAGE}
   * {@link WxConsts#CUSTOM_MSG_VOICE}
   * {@link WxConsts#CUSTOM_MSG_MUSIC}
   * {@link WxConsts#CUSTOM_MSG_VIDEO}
   * {@link WxConsts#CUSTOM_MSG_NEWS}
   * </pre>
   * @param msgtype
   */
  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getMedia_id() {
    return media_id;
  }
  public void setMedia_id(String media_id) {
    this.media_id = media_id;
  }
  public String getThumb_media_id() {
    return thumb_media_id;
  }
  public void setThumb_media_id(String thumb_media_id) {
    this.thumb_media_id = thumb_media_id;
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
  public String getMusicurl() {
    return musicurl;
  }
  public void setMusicurl(String musicurl) {
    this.musicurl = musicurl;
  }
  public String getHqmusicurl() {
    return hqmusicurl;
  }
  public void setHqmusicurl(String hqmusicurl) {
    this.hqmusicurl = hqmusicurl;
  }
  public List<WxArticle> getArticles() {
    return articles;
  }
  public void setArticles(List<WxArticle> articles) {
    this.articles = articles;
  }
  
  public String toJson() {
    return WxGsonBuilder.INSTANCE.create().toJson(this);
  }
  
  public static class WxArticle {
    
    private String title;
    private String description;
    private String url;
    private String picurl;
    
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
    public String getPicurl() {
      return picurl;
    }
    public void setPicurl(String picurl) {
      this.picurl = picurl;
    }
    
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

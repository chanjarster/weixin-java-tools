package chanjarster.weixin.bean;

import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 客服消息
 * @author chanjarster
 *
 */
public class WxCustomMessage {

  protected String touser;
  protected String msgtype;
  protected String content;
  protected String media_id;
  protected String thumb_media_id;
  protected String title;
  protected String description;
  protected String musicurl;
  protected String hqmusicurl;
  protected List<WxArticle> articles = new ArrayList<WxArticle>();
  
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
    
    protected String title;
    protected String description;
    protected String url;
    protected String picurl;
    
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
}

package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxMpXmlOutNewsMessage extends WxMpXmlOutMessage {

  @XmlElement(name = "ArticleCount")
  protected int articleCount;
  
  @XmlElementWrapper(name="Articles")
  @XmlElement(name = "item")
  protected final List<Item> articles = new ArrayList<Item>();
  
  public WxMpXmlOutNewsMessage() {
    this.msgType = WxConsts.XML_MSG_NEWS;
  }

  public int getArticleCount() {
    return articleCount;
  }

  public void addArticle(Item item) {
    this.articles.add(item);
    this.articleCount = this.articles.size();
  }
  
  public List<Item> getArticles() {
    return articles;
  }
  
  
  @XmlRootElement(name = "Item")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Item {
    
    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Title;

    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Description;

    @XmlElement(name="PicUrl")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String PicUrl;
    
    @XmlElement(name="Url")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String Url;
    
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

    public String getPicUrl() {
      return PicUrl;
    }

    public void setPicUrl(String picUrl) {
      PicUrl = picUrl;
    }

    public String getUrl() {
      return Url;
    }

    public void setUrl(String url) {
      Url = url;
    }

  }


}

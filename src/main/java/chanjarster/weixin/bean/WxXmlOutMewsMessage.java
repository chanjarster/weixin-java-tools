package chanjarster.weixin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.util.xml.AdapterCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxXmlOutMewsMessage extends WxXmlOutMessage {

  @XmlElement(name = "ArticleCount")
  protected int ArticleCount;
  
  @XmlElementWrapper(name="Articles")
  @XmlElement(name = "Item")
  protected final List<Item> Articles = new ArrayList<Item>();
  
  public WxXmlOutMewsMessage() {
    this.MsgType = WxConsts.XML_MSG_NEWS;
  }

  public int getArticleCount() {
    return ArticleCount;
  }

  public void addArticle(Item item) {
    this.Articles.add(item);
    this.ArticleCount = this.Articles.size();
  }
  
  public List<Item> getArticles() {
    return Articles;
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

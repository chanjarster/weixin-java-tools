package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WxMpMaterialNews implements Serializable {

  private List<WxMpMaterialNewsArticle> articles = new ArrayList<WxMpMaterialNewsArticle>();

  public List<WxMpMaterialNewsArticle> getArticles() {
    return articles;
  }

  public void addArticle(WxMpMaterialNewsArticle article) {
    this.articles.add(article);
  }

  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public boolean isEmpty() {
    return articles == null || articles.isEmpty();
  }

  /**
   * <pre>
   * 群发图文消息article
   * 1. thumbMediaId  (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
   * 2. author          图文消息的作者
   * 3. title           (必填) 图文消息的标题
   * 4. contentSourceUrl 在图文消息页面点击“阅读原文”后的页面链接
   * 5. content (必填)  图文消息页面的内容，支持HTML标签
   * 6. digest          图文消息的描述
   * 7. showCoverPic  是否显示封面，true为显示，false为不显示
   * 8. url           点击图文消息跳转链接
   * </pre>
   *
   * @author chanjarster
   */
  public static class WxMpMaterialNewsArticle {
    /**
     * (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
     */
    private String thumbMediaId;
    /**
     * 图文消息的作者
     */
    private String author;
    /**
     * (必填) 图文消息的标题
     */
    private String title;
    /**
     * 在图文消息页面点击“阅读原文”后的页面链接
     */
    private String contentSourceUrl;
    /**
     * (必填) 图文消息页面的内容，支持HTML标签
     */
    private String content;
    /**
     * 图文消息的描述
     */
    private String digest;
    /**
     * 是否显示封面，true为显示，false为不显示
     */
    private boolean showCoverPic;

    /**
     * 点击图文消息跳转链接
     * @return
    */
    private String url;

    public String getThumbMediaId() {
      return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
      this.thumbMediaId = thumbMediaId;
    }

    public String getAuthor() {
      return author;
    }

    public void setAuthor(String author) {
      this.author = author;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getContentSourceUrl() {
      return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
      this.contentSourceUrl = contentSourceUrl;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getDigest() {
      return digest;
    }

    public void setDigest(String digest) {
      this.digest = digest;
    }

    public boolean isShowCoverPic() {
      return showCoverPic;
    }

    public void setShowCoverPic(boolean showCoverPic) {
      this.showCoverPic = showCoverPic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
      return "WxMpMassNewsArticle [" + "thumbMediaId=" + thumbMediaId + ", author=" + author + ", title=" + title +
          ", contentSourceUrl=" + contentSourceUrl + ", content=" + content + ", digest=" + digest +
          ", showCoverPic=" + showCoverPic +", url=" + url + "]";
    }
  }

  @Override
  public String toString() {
    return "WxMpMaterialNews [" + "articles=" + articles + "]";
  }
}

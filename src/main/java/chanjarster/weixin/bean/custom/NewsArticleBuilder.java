package chanjarster.weixin.bean.custom;

import chanjarster.weixin.bean.WxCustomMessage;

/**
 * 图文消息文章builder
 * <pre>
 * 用法: WxCustomMessage.WxArticle m = WxCustomMessage.NEWS_ARTICLE()
 *                              .url(...)
 *                              .title(...)
 *                              .picurl(...)
 *                              .description(...)
 *                              .build();
 * </pre>
 * @author chanjarster
 *
 */
public final class NewsArticleBuilder {
  private String title;
  private String description;
  private String url;
  private String picurl;

  public NewsArticleBuilder url(String url) {
    this.url = url;
    return this;
  }

  public NewsArticleBuilder title(String title) {
    this.title = title;
    return this;
  }

  public NewsArticleBuilder description(String description) {
    this.description = description;
    return this;
  }

  public NewsArticleBuilder picurl(String picurl) {
    this.picurl = picurl;
    return this;
  }

  public WxCustomMessage.WxArticle build() {
    WxCustomMessage.WxArticle m = new WxCustomMessage.WxArticle();
    m.setPicurl(this.picurl);
    m.setTitle(title);
    m.setDescription(description);
    m.setUrl(this.url);
    return m;
  }
}

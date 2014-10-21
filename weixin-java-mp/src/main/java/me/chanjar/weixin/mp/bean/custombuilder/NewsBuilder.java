package me.chanjar.weixin.mp.bean.custombuilder;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.mp.api.WxConsts;
import me.chanjar.weixin.mp.bean.WxCustomMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxCustomMessage.WxArticle> articles = new ArrayList<WxCustomMessage.WxArticle>();
  
  public NewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxCustomMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}

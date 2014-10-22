package me.chanjar.weixin.cp.bean.messagebuilder;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.cp.api.WxCpConsts;
import me.chanjar.weixin.cp.bean.WxCpMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxCpMessage.WxArticle> articles = new ArrayList<WxCpMessage.WxArticle>();
  
  public NewsBuilder() {
    this.msgType = WxCpConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxCpMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}

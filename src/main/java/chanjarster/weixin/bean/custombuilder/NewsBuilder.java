package chanjarster.weixin.bean.custombuilder;

import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxCustomMessage.WxArticle;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).touser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxArticle> articles = new ArrayList<WxArticle>();
  
  public NewsBuilder() {
    this.msgtype = WxConsts.CUSTOM_MSG_NEWS;
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

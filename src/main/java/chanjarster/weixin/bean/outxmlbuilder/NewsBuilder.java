package chanjarster.weixin.bean.outxmlbuilder;

import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.bean.WxXmlOutMewsMessage;
import chanjarster.weixin.bean.WxXmlOutMewsMessage.Item;

/**
 * 图文消息builder
 * @author chanjarster
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxXmlOutMewsMessage> {

  protected final List<Item> articles = new ArrayList<Item>();
  
  public NewsBuilder addArticle(Item item) {
    this.articles.add(item);
    return this;
  }
  
  public WxXmlOutMewsMessage build() {
    WxXmlOutMewsMessage m = new WxXmlOutMewsMessage();
    for(Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}

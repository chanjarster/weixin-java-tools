package me.chanjar.weixin.enterprise.bean.outxmlbuilder;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.enterprise.bean.WxXmlOutMewsMessage;
import me.chanjar.weixin.enterprise.bean.WxXmlOutMewsMessage.Item;

/**
 * 图文消息builder
 * @author Daniel Qian
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

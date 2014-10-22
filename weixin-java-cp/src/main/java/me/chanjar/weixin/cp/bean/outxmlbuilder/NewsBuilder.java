package me.chanjar.weixin.cp.bean.outxmlbuilder;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.cp.bean.WxCpXmlOutMewsMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMewsMessage.Item;

/**
 * 图文消息builder
 * @author Daniel Qian
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxCpXmlOutMewsMessage> {

  protected final List<Item> articles = new ArrayList<Item>();
  
  public NewsBuilder addArticle(Item item) {
    this.articles.add(item);
    return this;
  }
  
  public WxCpXmlOutMewsMessage build() {
    WxCpXmlOutMewsMessage m = new WxCpXmlOutMewsMessage();
    for(Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}

package me.chanjar.weixin.mp.bean.outxmlbuilder;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.mp.bean.WxXmlOutMewsMessage;

/**
 * 图文消息builder
 * @author chanjarster
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxXmlOutMewsMessage> {

  protected final List<WxXmlOutMewsMessage.Item> articles = new ArrayList<WxXmlOutMewsMessage.Item>();
  
  public NewsBuilder addArticle(WxXmlOutMewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }
  
  public WxXmlOutMewsMessage build() {
    WxXmlOutMewsMessage m = new WxXmlOutMewsMessage();
    for(WxXmlOutMewsMessage.Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}

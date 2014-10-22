package me.chanjar.weixin.mp.bean.outxmlbuilder;

import me.chanjar.weixin.mp.bean.WxMpXmlOutMewsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息builder
 * @author chanjarster
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxMpXmlOutMewsMessage> {

  protected final List<WxMpXmlOutMewsMessage.Item> articles = new ArrayList<WxMpXmlOutMewsMessage.Item>();
  
  public NewsBuilder addArticle(WxMpXmlOutMewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }
  
  public WxMpXmlOutMewsMessage build() {
    WxMpXmlOutMewsMessage m = new WxMpXmlOutMewsMessage();
    for(WxMpXmlOutMewsMessage.Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}

package me.chanjar.weixin.bean;

import me.chanjar.weixin.bean.WxXmlOutMessage;
import me.chanjar.weixin.bean.WxXmlOutMewsMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxXmlOutINewsMessageTest {

  public void test() {
    WxXmlOutMewsMessage m = new WxXmlOutMewsMessage();
    m.setCreateTime(1122l);
    m.setFromUserName("fromUser");
    m.setToUserName("toUser");
    
    WxXmlOutMewsMessage.Item item = new WxXmlOutMewsMessage.Item();
    item.setDescription("description");
    item.setPicUrl("picUrl");
    item.setTitle("title");
    item.setUrl("url");
    m.addArticle(item);
    m.addArticle(item);
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[toUser]]></ToUserName>"
        + "<FromUserName><![CDATA[fromUser]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[news]]></MsgType>"
        + "    <ArticleCount>2</ArticleCount>"
        + "    <Articles>"
        + "        <Item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </Item>"
        + "        <Item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </Item>"
        + "    </Articles>"
        + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }
  
  public void testBuild() {
    WxXmlOutMewsMessage.Item item = new WxXmlOutMewsMessage.Item();
    item.setDescription("description");
    item.setPicUrl("picUrl");
    item.setTitle("title");
    item.setUrl("url");
    
    WxXmlOutMewsMessage m = WxXmlOutMessage.NEWS()
          .fromUser("fromUser")
          .touser("toUser")
          .addArticle(item)
          .addArticle(item)
          .build();
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[toUser]]></ToUserName>"
        + "<FromUserName><![CDATA[fromUser]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[news]]></MsgType>"
        + "    <ArticleCount>2</ArticleCount>"
        + "    <Articles>"
        + "        <Item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </Item>"
        + "        <Item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </Item>"
        + "    </Articles>"
        + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(
        m
        .toXml()
        .replaceAll("\\s", "")
        .replaceAll("<CreateTime>.*?</CreateTime>", ""), 
        expected
        .replaceAll("\\s", "")
        .replaceAll("<CreateTime>.*?</CreateTime>", "")
        );
  }
  
}

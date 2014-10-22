package me.chanjar.weixin.mp.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxXmlOutNewsMessageTest {

  public void test() {
    WxMpXmlOutMewsMessage m = new WxMpXmlOutMewsMessage();
    m.setCreateTime(1122l);
    m.setFromUserName("fromUser");
    m.setToUserName("toUser");
    
    WxMpXmlOutMewsMessage.Item item = new WxMpXmlOutMewsMessage.Item();
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
        + "        <item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </item>"
        + "        <item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </item>"
        + "    </Articles>"
        + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }
  
  public void testBuild() {
    WxMpXmlOutMewsMessage.Item item = new WxMpXmlOutMewsMessage.Item();
    item.setDescription("description");
    item.setPicUrl("picUrl");
    item.setTitle("title");
    item.setUrl("url");
    
    WxMpXmlOutMewsMessage m = WxMpXmlOutMessage.NEWS()
          .fromUser("fromUser")
          .toUser("toUser")
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
        + "        <item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </item>"
        + "        <item>"
        + "            <Title><![CDATA[title]]></Title>"
        + "            <Description><![CDATA[description]]></Description>"
        + "            <PicUrl><![CDATA[picUrl]]></PicUrl>"
        + "            <Url><![CDATA[url]]></Url>"
        + "        </item>"
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

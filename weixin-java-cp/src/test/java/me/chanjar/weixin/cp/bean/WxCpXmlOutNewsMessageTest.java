package me.chanjar.weixin.cp.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxCpXmlOutNewsMessageTest {

  public void test() {
    WxCpXmlOutMewsMessage m = new WxCpXmlOutMewsMessage();
    m.setCreateTime(1122l);
    m.setFromUserName("fromUser");
    m.setToUserName("toUser");
    
    WxCpXmlOutMewsMessage.Item item = new WxCpXmlOutMewsMessage.Item();
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
    WxCpXmlOutMewsMessage.Item item = new WxCpXmlOutMewsMessage.Item();
    item.setDescription("description");
    item.setPicUrl("picUrl");
    item.setTitle("title");
    item.setUrl("url");
    
    WxCpXmlOutMewsMessage m = WxCpXmlOutMessage.NEWS()
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

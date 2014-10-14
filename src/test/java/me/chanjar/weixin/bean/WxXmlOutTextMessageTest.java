package me.chanjar.weixin.bean;

import me.chanjar.weixin.bean.WxXmlOutMessage;
import me.chanjar.weixin.bean.WxXmlOutTextMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxXmlOutTextMessageTest {

  public void test() {
    WxXmlOutTextMessage m = new WxXmlOutTextMessage();
    m.setContent("content");
    m.setCreateTime(1122l);
    m.setFromUserName("from");
    m.setToUserName("to");
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[to]]></ToUserName>"
        + "<FromUserName><![CDATA[from]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[text]]></MsgType>"
        + "<Content><![CDATA[content]]></Content>"
        + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }
  
  public void testBuild() {
    WxXmlOutTextMessage m = WxXmlOutMessage.TEXT().content("content").fromUser("from").touser("to").build();
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[to]]></ToUserName>"
        + "<FromUserName><![CDATA[from]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[text]]></MsgType>"
        + "<Content><![CDATA[content]]></Content>"
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

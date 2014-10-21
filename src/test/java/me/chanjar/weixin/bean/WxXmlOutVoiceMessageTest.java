package me.chanjar.weixin.bean;

import me.chanjar.weixin.bean.WxXmlOutMessage;
import me.chanjar.weixin.bean.WxXmlOutVoiceMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxXmlOutVoiceMessageTest {

  public void test() {
    WxXmlOutVoiceMessage m = new WxXmlOutVoiceMessage();
    m.setMediaId("ddfefesfsdfef");
    m.setCreateTime(1122l);
    m.setFromUserName("from");
    m.setToUserName("to");
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[to]]></ToUserName>"
        + "<FromUserName><![CDATA[from]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[voice]]></MsgType>"
        + "<Voice><MediaId><![CDATA[ddfefesfsdfef]]></MediaId></Voice>"
        + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }
  
  public void testBuild() {
    WxXmlOutVoiceMessage m = WxXmlOutMessage.VOICE().mediaId("ddfefesfsdfef").fromUser("from").touser("to").build();
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[to]]></ToUserName>"
        + "<FromUserName><![CDATA[from]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[voice]]></MsgType>"
        + "<Voice><MediaId><![CDATA[ddfefesfsdfef]]></MediaId></Voice>"
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

package me.chanjar.weixin.bean;

import me.chanjar.weixin.bean.WxXmlOutMessage;
import me.chanjar.weixin.bean.WxXmlOutMusicMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxXmlOutIMusicMessageTest {

  public void test() {
    WxXmlOutMusicMessage m = new WxXmlOutMusicMessage();
    m.setTitle("title");
    m.setDescription("ddfff");
    m.setHQMusicUrl("hQMusicUrl");
    m.setMusicUrl("musicUrl");
    m.setThumbMediaId("thumbMediaId");
    m.setCreateTime(1122l);
    m.setFromUserName("fromUser");
    m.setToUserName("toUser");
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[toUser]]></ToUserName>"
        + "<FromUserName><![CDATA[fromUser]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[music]]></MsgType>"
        + "<Music>"
        + "        <Title><![CDATA[title]]></Title>"
        + "        <Description><![CDATA[ddfff]]></Description>"
        + "        <ThumbMediaId><![CDATA[thumbMediaId]]></ThumbMediaId>"
        + "        <MusicUrl><![CDATA[musicUrl]]></MusicUrl>"
        + "        <HQMusicUrl><![CDATA[hQMusicUrl]]></HQMusicUrl>"
        + "    </Music>"
        + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }
  
  public void testBuild() {
    WxXmlOutMusicMessage m = WxXmlOutMessage.MUSIC()
          .fromUser("fromUser")
          .touser("toUser")
          .title("title")
          .description("ddfff")
          .hqmusicUrl("hQMusicUrl")
          .musicUrl("musicUrl")
          .thumbMediaId("thumbMediaId")
          .build();
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
        + "<xml>"
        + "<ToUserName><![CDATA[toUser]]></ToUserName>"
        + "<FromUserName><![CDATA[fromUser]]></FromUserName>"
        + "<CreateTime>1122</CreateTime>"
        + "<MsgType><![CDATA[music]]></MsgType>"
        + "<Music>"
        + "        <Title><![CDATA[title]]></Title>"
        + "        <Description><![CDATA[ddfff]]></Description>"
        + "        <ThumbMediaId><![CDATA[thumbMediaId]]></ThumbMediaId>"
        + "        <MusicUrl><![CDATA[musicUrl]]></MusicUrl>"
        + "        <HQMusicUrl><![CDATA[hQMusicUrl]]></HQMusicUrl>"
        + "    </Music>"
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

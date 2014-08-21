package chanjarster.weixin.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxXmlMessage;

@Test
public class WxXmlMessageTest {

  public void testFromXml() {

    String xml = "<xml>"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName> "
                + "<CreateTime>1348831860</CreateTime>"
                + "<MsgType><![CDATA[text]]></MsgType>"
                + "<Content><![CDATA[this is a test]]></Content>"
                + "<MsgId>1234567890123456</MsgId>"
                + "<PicUrl><![CDATA[this is a url]]></PicUrl>"
                + "<MediaId><![CDATA[media_id]]></MediaId>"
                + "<Format><![CDATA[Format]]></Format>"
                + "<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
                + "<Location_X>23.134521</Location_X>"
                + "<Location_Y>113.358803</Location_Y>"
                + "<Scale>20</Scale>"
                + "<Label><![CDATA[位置信息]]></Label>"
                + "<Description><![CDATA[公众平台官网链接]]></Description>"
                + "<Url><![CDATA[url]]></Url>"
                + "<Title><![CDATA[公众平台官网链接]]></Title>"
                + "<Event><![CDATA[subscribe]]></Event>"
                + "<EventKey><![CDATA[qrscene_123123]]></EventKey>"
                + "<Ticket><![CDATA[TICKET]]></Ticket>"
                + "<Latitude>23.137466</Latitude>"
                + "<Longitude>113.352425</Longitude>"
                + "<Precision>119.385040</Precision>"
                + "</xml>";
    WxXmlMessage wxMessage = WxXmlMessage.fromXml(xml);
    Assert.assertEquals(wxMessage.getToUserName(), "toUser");
    Assert.assertEquals(wxMessage.getFromUserName(), "fromUser");
    Assert.assertEquals(wxMessage.getCreateTime(), new Long(1348831860l));
    Assert.assertEquals(wxMessage.getMsgType(), WxConsts.TEXT);
    Assert.assertEquals(wxMessage.getContent(), "this is a test");
    Assert.assertEquals(wxMessage.getMsgId(), new Long(1234567890123456l));
    Assert.assertEquals(wxMessage.getPicUrl(), "this is a url");
    Assert.assertEquals(wxMessage.getMediaId(), "media_id");
    Assert.assertEquals(wxMessage.getFormat(), "Format");
    Assert.assertEquals(wxMessage.getThumbMediaId(), "thumb_media_id");
    Assert.assertEquals(wxMessage.getLocation_X(), new Double(23.134521d));
    Assert.assertEquals(wxMessage.getLocation_Y(), new Double(113.358803d));
    Assert.assertEquals(wxMessage.getScale(), new Double(20));
    Assert.assertEquals(wxMessage.getLabel(), "位置信息");
    Assert.assertEquals(wxMessage.getDescription(), "公众平台官网链接");
    Assert.assertEquals(wxMessage.getUrl(), "url");
    Assert.assertEquals(wxMessage.getTitle(), "公众平台官网链接");
    Assert.assertEquals(wxMessage.getEvent(), "subscribe");
    Assert.assertEquals(wxMessage.getEventKey(), "qrscene_123123");
    Assert.assertEquals(wxMessage.getTicket(), "TICKET");
    Assert.assertEquals(wxMessage.getLatitude(), new Double(23.137466));
    Assert.assertEquals(wxMessage.getLongitude(), new Double(113.352425));
    Assert.assertEquals(wxMessage.getPrecision(), new Double(119.385040));
  }
  
  public void testToXml() {
    WxXmlMessage wxMessage = new WxXmlMessage();
    wxMessage.setToUserName("toUser");
    wxMessage.setFromUserName("fromUser");
    wxMessage.setCreateTime(new Long(1348831860l));
    wxMessage.setMsgType(WxConsts.TEXT);
    wxMessage.setContent("this is a test");
    wxMessage.setMsgId(new Long(1234567890123456l));
    wxMessage.setPicUrl("this is a url");
    wxMessage.setMediaId("media_id");
    wxMessage.setFormat("Format");
    wxMessage.setThumbMediaId("thumb_media_id");
    wxMessage.setLocation_X(new Double(23.134521d));
    wxMessage.setLocation_Y(new Double(113.358803d));
    wxMessage.setScale(new Double(20));
    wxMessage.setLabel("位置信息");
    wxMessage.setDescription("公众平台官网链接");
    wxMessage.setUrl("url");
    wxMessage.setTitle("公众平台官网链接");
    wxMessage.setEvent("subscribe");
    wxMessage.setEventKey("qrscene_123123");
    wxMessage.setTicket("TICKET");
    wxMessage.setLatitude(new Double(23.137466));
    wxMessage.setLongitude(new Double(113.352425));
    wxMessage.setPrecision(new Double(119.385040));
    
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<xml>\n"
        + "    <ToUserName><![CDATA[toUser]]></ToUserName>\n"
        + "    <FromUserName><![CDATA[fromUser]]></FromUserName>\n"
        + "    <CreateTime>1348831860</CreateTime>\n"
        + "    <MsgType><![CDATA[text]]></MsgType>\n"
        + "    <Content><![CDATA[this is a test]]></Content>\n"
        + "    <MsgId>1234567890123456</MsgId>\n"
        + "    <PicUrl><![CDATA[this is a url]]></PicUrl>\n"
        + "    <MediaId><![CDATA[media_id]]></MediaId>\n"
        + "    <Format><![CDATA[Format]]></Format>\n"
        + "    <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>\n"
        + "    <Location_X>23.134521</Location_X>\n"
        + "    <Location_Y>113.358803</Location_Y>\n"
        + "    <Scale>20.0</Scale>\n"
        + "    <Label><![CDATA[位置信息]]></Label>\n"
        + "    <Title><![CDATA[公众平台官网链接]]></Title>\n"
        + "    <Description><![CDATA[公众平台官网链接]]></Description>\n"
        + "    <Url><![CDATA[url]]></Url>\n"
        + "    <Event><![CDATA[subscribe]]></Event>\n"
        + "    <EventKey><![CDATA[qrscene_123123]]></EventKey>\n"
        + "    <Ticket><![CDATA[TICKET]]></Ticket>\n"
        + "    <Latitude>23.137466</Latitude>\n"
        + "    <Longitude>113.352425</Longitude>\n"
        + "    <Precision>119.38504</Precision>\n"
        + "</xml>\n";
    Assert.assertEquals(wxMessage.toXml(), xml);
  }
}

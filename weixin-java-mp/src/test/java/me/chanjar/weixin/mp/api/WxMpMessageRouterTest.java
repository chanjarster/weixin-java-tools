package me.chanjar.weixin.mp.api;

import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * 测试消息路由器
 * @author chanjarster
 *
 */
@Test
public class WxMpMessageRouterTest {
  
  @Test(enabled = false)
  public void prepare(boolean async, StringBuffer sb, WxMpMessageRouter router) {
    router
      .rule()
        .async(async)
        .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1").content("CONTENT_1")
        .handler(new WxEchoMpMessageHandler(sb, "COMBINE_4"))
      .end()
      .rule()
        .async(async)
        .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1")
        .handler(new WxEchoMpMessageHandler(sb, "COMBINE_3"))
      .end()
      .rule()
        .async(async)
        .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK)
        .handler(new WxEchoMpMessageHandler(sb, "COMBINE_2"))
      .end()
      .rule().async(async).msgType(WxConsts.XML_MSG_TEXT).handler(new WxEchoMpMessageHandler(sb, WxConsts.XML_MSG_TEXT)).end()
      .rule().async(async).event(WxConsts.EVT_CLICK).handler(new WxEchoMpMessageHandler(sb, WxConsts.EVT_CLICK)).end()
      .rule().async(async).eventKey("KEY_1").handler(new WxEchoMpMessageHandler(sb, "KEY_1")).end()
      .rule().async(async).content("CONTENT_1").handler(new WxEchoMpMessageHandler(sb, "CONTENT_1")).end()
      .rule().async(async).rContent(".*bc.*").handler(new WxEchoMpMessageHandler(sb, "abcd")).end()
      .rule().async(async).handler(new WxEchoMpMessageHandler(sb, "ALL")).end();
    ;
  }
  
  @Test(dataProvider="messages-1")
  public void testSync(WxMpXmlMessage message, String expected) {
    StringBuffer sb = new StringBuffer();
    WxMpMessageRouter router = new WxMpMessageRouter(null);
    prepare(false, sb, router);
    router.route(message);
    Assert.assertEquals(sb.toString(), expected);
  }
  
  @Test(dataProvider="messages-1")
  public void testAsync(WxMpXmlMessage message, String expected) throws InterruptedException {
    StringBuffer sb = new StringBuffer();
    WxMpMessageRouter router = new WxMpMessageRouter(null);
    prepare(true,  sb, router);
    router.route(message);
    Thread.sleep(500l);
    Assert.assertEquals(sb.toString(), expected);
  }
  
  public void testConcurrency() throws InterruptedException {
    final WxMpMessageRouter router = new WxMpMessageRouter(null);
    router.rule().handler(new WxMpMessageHandler() {
      @Override
      public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService) {
        return null;
      }
    }).end();
    
    final WxMpXmlMessage m = new WxMpXmlMessage();
    Runnable r = new Runnable() {
      @Override
      public void run() {
        router.route(m);
        try {
          Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
      }
    };
    for (int i = 0; i < 10; i++) {
      new Thread(r).start();
    }
    
    Thread.sleep(1000l * 2);
  }
  @DataProvider(name="messages-1")
  public Object[][] messages2() {
    WxMpXmlMessage message1 = new WxMpXmlMessage();
    message1.setMsgType(WxConsts.XML_MSG_TEXT);
  
    WxMpXmlMessage message2 = new WxMpXmlMessage();
    message2.setEvent(WxConsts.EVT_CLICK);
    
    WxMpXmlMessage message3 = new WxMpXmlMessage();
    message3.setEventKey("KEY_1");
    
    WxMpXmlMessage message4 = new WxMpXmlMessage();
    message4.setContent("CONTENT_1");
    
    WxMpXmlMessage message5 = new WxMpXmlMessage();
    message5.setContent("BLA");
    
    WxMpXmlMessage message6 =  new WxMpXmlMessage();
    message6.setContent("abcd");
    
    WxMpXmlMessage c2 = new WxMpXmlMessage();
    c2.setMsgType(WxConsts.XML_MSG_TEXT);
    c2.setEvent(WxConsts.EVT_CLICK);
    
    WxMpXmlMessage c3 = new WxMpXmlMessage();
    c3.setMsgType(WxConsts.XML_MSG_TEXT);
    c3.setEvent(WxConsts.EVT_CLICK);
    c3.setEventKey("KEY_1");
    
    WxMpXmlMessage c4 = new WxMpXmlMessage();
    c4.setMsgType(WxConsts.XML_MSG_TEXT);
    c4.setEvent(WxConsts.EVT_CLICK);
    c4.setEventKey("KEY_1");
    c4.setContent("CONTENT_1");
    
    return new Object[][] {
        new Object[] { message1, WxConsts.XML_MSG_TEXT + "," },
        new Object[] { message2, WxConsts.EVT_CLICK + "," },
        new Object[] { message3, "KEY_1," },
        new Object[] { message4, "CONTENT_1," },
        new Object[] { message5, "ALL," },
        new Object[] { message6, "abcd," },
        new Object[] { c2, "COMBINE_2," },
        new Object[] { c3, "COMBINE_3," },
        new Object[] { c4, "COMBINE_4," }
    };
    
  }

  public static class WxEchoMpMessageHandler implements WxMpMessageHandler {

    private StringBuffer sb;
    private String echoStr;

    public WxEchoMpMessageHandler(StringBuffer sb, String echoStr) {
      this.sb = sb;
      this.echoStr = echoStr;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService) {
      sb.append(this.echoStr).append(',');
      return null;
    }

  }

}

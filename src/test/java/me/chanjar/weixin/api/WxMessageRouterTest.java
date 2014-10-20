package me.chanjar.weixin.api;

import java.util.Map;

import me.chanjar.weixin.api.WxConsts;
import me.chanjar.weixin.api.WxMessageHandler;
import me.chanjar.weixin.api.WxMessageRouter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import me.chanjar.weixin.bean.WxXmlMessage;
import me.chanjar.weixin.bean.WxXmlOutMessage;

/**
 * 测试消息路由器
 * @author chanjarster
 *
 */
@Test
public class WxMessageRouterTest {
  
  @Test(enabled = false)
  public void prepare(boolean async, StringBuffer sb, WxMessageRouter router) {
    router
      .rule()
        .async(async)
        .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1").content("CONTENT_1")
        .handler(new WxEchoMessageHandler(sb, "COMBINE_4"))
      .end()
      .rule()
        .async(async)
        .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1")
        .handler(new WxEchoMessageHandler(sb, "COMBINE_3"))
      .end()
      .rule()
        .async(async)
        .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK)
        .handler(new WxEchoMessageHandler(sb, "COMBINE_2"))
      .end()
      .rule().async(async).msgType(WxConsts.XML_MSG_TEXT).handler(new WxEchoMessageHandler(sb, WxConsts.XML_MSG_TEXT)).end()
      .rule().async(async).event(WxConsts.EVT_CLICK).handler(new WxEchoMessageHandler(sb, WxConsts.EVT_CLICK)).end()
      .rule().async(async).eventKey("KEY_1").handler(new WxEchoMessageHandler(sb, "KEY_1")).end()
      .rule().async(async).content("CONTENT_1").handler(new WxEchoMessageHandler(sb, "CONTENT_1")).end()
      .rule().async(async).rContent(".*bc.*").handler(new WxEchoMessageHandler(sb, "abcd")).end()
      .rule().async(async).handler(new WxEchoMessageHandler(sb, "ALL")).end();
    ;
  }
  
  @Test(dataProvider="messages-1")
  public void testSync(WxXmlMessage message, String expected) {
    StringBuffer sb = new StringBuffer();
    WxMessageRouter router = new WxMessageRouter();
    prepare(false, sb, router);
    router.route(message);
    Assert.assertEquals(sb.toString(), expected);
  }
  
  @Test(dataProvider="messages-1")
  public void testAsync(WxXmlMessage message, String expected) throws InterruptedException {
    StringBuffer sb = new StringBuffer();
    WxMessageRouter router = new WxMessageRouter();
    prepare(true,  sb, router);
    router.route(message);
    Thread.sleep(500l);
    Assert.assertEquals(sb.toString(), expected);
  }
  
  public void testConcurrency() throws InterruptedException {
    final WxMessageRouter router = new WxMessageRouter();
    router.rule().handler(new WxMessageHandler() {
      @Override
      public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {
        return null;
      }
    }).end();
    
    final WxXmlMessage m = new WxXmlMessage();
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
    WxXmlMessage message1 = new WxXmlMessage();
    message1.setMsgType(WxConsts.XML_MSG_TEXT);
  
    WxXmlMessage message2 = new WxXmlMessage();
    message2.setEvent(WxConsts.EVT_CLICK);
    
    WxXmlMessage message3 = new WxXmlMessage();
    message3.setEventKey("KEY_1");
    
    WxXmlMessage message4 = new WxXmlMessage();
    message4.setContent("CONTENT_1");
    
    WxXmlMessage message5 = new WxXmlMessage();
    message5.setContent("BLA");
    
    WxXmlMessage message6 =  new WxXmlMessage();
    message6.setContent("abcd");
    
    WxXmlMessage c2 = new WxXmlMessage();
    c2.setMsgType(WxConsts.XML_MSG_TEXT);
    c2.setEvent(WxConsts.EVT_CLICK);
    
    WxXmlMessage c3 = new WxXmlMessage();
    c3.setMsgType(WxConsts.XML_MSG_TEXT);
    c3.setEvent(WxConsts.EVT_CLICK);
    c3.setEventKey("KEY_1");
    
    WxXmlMessage c4 = new WxXmlMessage();
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

  public static class WxEchoMessageHandler implements WxMessageHandler {

    private StringBuffer sb;
    private String echoStr;

    public WxEchoMessageHandler(StringBuffer sb, String echoStr) {
      this.sb = sb;
      this.echoStr = echoStr;
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {
      sb.append(this.echoStr).append(',');
      return null;
    }

  }

}

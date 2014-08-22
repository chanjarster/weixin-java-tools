package chanjarster.weixin.api;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import chanjarster.weixin.bean.WxXmlMessage;

@Test
public class WxMessageRouterTest {
  
  protected StringBuilder sb;
  protected WxMessageRouter router;
  
  @BeforeMethod
  public void prepare() {
    this.sb = new StringBuilder();
    this.router = new WxMessageRouter();
    router
      .rule()
        .msgType(WxConsts.MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1").content("CONTENT_1")
        .handler(new WxEchoMessageHandler(sb, "COMBINE_4"))
      .end()
      .rule()
        .msgType(WxConsts.MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1")
        .handler(new WxEchoMessageHandler(sb, "COMBINE_3"))
      .end()
      .rule()
        .msgType(WxConsts.MSG_TEXT).event(WxConsts.EVT_CLICK)
        .handler(new WxEchoMessageHandler(sb, "COMBINE_2"))
      .end()
      .rule().msgType(WxConsts.MSG_TEXT).handler(new WxEchoMessageHandler(sb, WxConsts.MSG_TEXT)).end()
      .rule().event(WxConsts.EVT_CLICK).handler(new WxEchoMessageHandler(sb, WxConsts.EVT_CLICK)).end()
      .rule().eventKey("KEY_1").handler(new WxEchoMessageHandler(sb, "KEY_1")).end()
      .rule().content("CONTENT_1").handler(new WxEchoMessageHandler(sb, "CONTENT_1")).end()
      .rule().handler(new WxEchoMessageHandler(sb, "ALL")).end();
    ;
  }
  
  @Test(dataProvider="messages-1")
  public void testSimple(WxXmlMessage message, String expected) {
    router.route(message);
    Assert.assertEquals(sb.toString(), expected);
  }
  
  @Test()
  public void test() {
    
  }
  @DataProvider(name="messages-1")
  public Object[][] messages2() {
    WxXmlMessage message1 = new WxXmlMessage();
    message1.setMsgType(WxConsts.MSG_TEXT);
  
    WxXmlMessage message2 = new WxXmlMessage();
    message2.setEvent(WxConsts.EVT_CLICK);
    
    WxXmlMessage message3 = new WxXmlMessage();
    message3.setEventKey("KEY_1");
    
    WxXmlMessage message4 = new WxXmlMessage();
    message4.setContent("CONTENT_1");
    
    WxXmlMessage message5 = new WxXmlMessage();
    message5.setContent("BLA");
    
    WxXmlMessage c2 = new WxXmlMessage();
    c2.setMsgType(WxConsts.MSG_TEXT);
    c2.setEvent(WxConsts.EVT_CLICK);
    
    WxXmlMessage c3 = new WxXmlMessage();
    c3.setMsgType(WxConsts.MSG_TEXT);
    c3.setEvent(WxConsts.EVT_CLICK);
    c3.setEventKey("KEY_1");
    
    WxXmlMessage c4 = new WxXmlMessage();
    c4.setMsgType(WxConsts.MSG_TEXT);
    c4.setEvent(WxConsts.EVT_CLICK);
    c4.setEventKey("KEY_1");
    c4.setContent("CONTENT_1");
    
    return new Object[][] {
        new Object[] { message1, WxConsts.MSG_TEXT + "," },
        new Object[] { message2, WxConsts.EVT_CLICK + "," }, 
        new Object[] { message3, "KEY_1," },
        new Object[] { message4, "CONTENT_1," },
        new Object[] { message5, "ALL," },
        new Object[] { c2, "COMBINE_2," },
        new Object[] { c3, "COMBINE_3," },
        new Object[] { c4, "COMBINE_4," }
    };
    
  }

  public static class WxEchoMessageHandler implements WxMessageHandler {

    private StringBuilder sb;
    private String echoStr;

    public WxEchoMessageHandler(StringBuilder sb, String echoStr) {
      this.sb = sb;
      this.echoStr = echoStr;
    }

    @Override
    public void handle(WxXmlMessage wxMessage, Map<String, Object> context) {
      sb.append(this.echoStr).append(',');
    }

  }

}

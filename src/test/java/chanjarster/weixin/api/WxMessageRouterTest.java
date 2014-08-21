package chanjarster.weixin.api;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import chanjarster.weixin.bean.WxXmlMessage;

@Test
public class WxMessageRouterTest {

  public void testSimple() {
    StringBuilder sb = new StringBuilder();
    WxMessageRouter router = new WxMessageRouter();
    router
      .rule().msgType(WxConsts.MSG_TEXT).handler(new WxEchoMessageHandler(sb, WxConsts.MSG_TEXT)).end()
      .rule().msgType(WxConsts.MSG_IMAGE).handler(new WxEchoMessageHandler(sb, WxConsts.MSG_IMAGE)).end()
    ;

    
    WxXmlMessage message = new WxXmlMessage();
    message.setMsgType(WxConsts.MSG_TEXT);
    router.route(message);
    Assert.assertEquals(sb.toString(), WxConsts.MSG_TEXT + ",");
    
    
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

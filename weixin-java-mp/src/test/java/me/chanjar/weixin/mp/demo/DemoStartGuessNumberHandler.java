package me.chanjar.weixin.mp.demo;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.Map;
import java.util.Random;

public class DemoStartGuessNumberHandler implements WxMpMessageHandler {

  private Random random = new Random();

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) {

    WxSession session = sessionManager.getSession(wxMessage.getFromUserName());

    if (session.getAttribute("guessing") == null) {
      WxMpCustomMessage m = WxMpCustomMessage
          .TEXT()
          .toUser(wxMessage.getFromUserName())
          .content("请猜一个100以内的数字")
          .build();
      try {
        wxMpService.customMessageSend(m);
      } catch (WxErrorException e) {
        e.printStackTrace();
      }
    } else {
      WxMpCustomMessage m = WxMpCustomMessage
          .TEXT()
          .toUser(wxMessage.getFromUserName())
          .content("放弃了吗？那请重新猜一个100以内的数字")
          .build();
      try {
        wxMpService.customMessageSend(m);
      } catch (WxErrorException e) {
        e.printStackTrace();
      }
    }

    session.setAttribute("guessing", Boolean.TRUE);
    session.setAttribute("number", random.nextInt(100));
    return null;

  }
}

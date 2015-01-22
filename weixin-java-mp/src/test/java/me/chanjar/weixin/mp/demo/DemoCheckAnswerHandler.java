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

public class DemoCheckAnswerHandler implements WxMpMessageHandler {

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) {

    WxSession session = sessionManager.getSession(wxMessage.getFromUserName());

    if (session.getAttribute("guessing") == null) {
      return null;
    }
    boolean guessing = (Boolean) session.getAttribute("guessing");
    if (!guessing) {
      return null;
    }

    int answer = (Integer) session.getAttribute("number");
    int guessNumber = Integer.valueOf(wxMessage.getContent());
    if (guessNumber < answer) {
      WxMpCustomMessage m = WxMpCustomMessage
          .TEXT()
          .toUser(wxMessage.getFromUserName())
          .content("小了")
          .build();
      try {
        wxMpService.customMessageSend(m);
      } catch (WxErrorException e) {
        e.printStackTrace();
      }

    } else if (guessNumber > answer) {
      WxMpCustomMessage m = WxMpCustomMessage
          .TEXT()
          .toUser(wxMessage.getFromUserName())
          .content("大了")
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
          .content("Bingo!")
          .build();
      try {
        session.removeAttribute("guessing");
        wxMpService.customMessageSend(m);
      } catch (WxErrorException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

}

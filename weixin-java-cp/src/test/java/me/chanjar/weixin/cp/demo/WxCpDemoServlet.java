package me.chanjar.weixin.cp.demo;

import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.cp.api.*;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Daniel Qian
 */
public class WxCpDemoServlet extends HttpServlet {

  protected WxCpConfigStorage wxCpConfigStorage;
  protected WxCpService wxCpService;
  protected WxCpMessageRouter wxCpMessageRouter;

  @Override public void init() throws ServletException {
    //
    super.init();
    try {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(is1);

      wxCpConfigStorage = config;
      wxCpService = new WxCpServiceImpl();
      wxCpService.setWxCpConfigStorage(config);

      WxCpMessageHandler handler = new WxCpMessageHandler() {
        @Override public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService) {
          WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                  .TEXT()
                  .content("测试加密消息")
                  .fromUser(wxMessage.getToUserName())
                  .toUser(wxMessage.getFromUserName())
              .build();
          return m;
        }
      };

      wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
      wxCpMessageRouter
          .rule()
          .async(false)
          .content("哈哈") // 拦截内容为“哈哈”的消息
          .handler(handler)
          .end();

    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String msgSignature = request.getParameter("msg_signature");
    String nonce = request.getParameter("nonce");
    String timestamp = request.getParameter("timestamp");
    String echostr = request.getParameter("echostr");

    if (StringUtils.isNotBlank(echostr)) {
      if (!wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
        // 消息签名不正确，说明不是公众平台发过来的消息
        response.getWriter().println("非法请求");
        return;
      }
      WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
      String plainText = cryptUtil.decrypt(echostr);
      // 说明是一个仅仅用来验证的请求，回显echostr
      response.getWriter().println(plainText);
      return;
    }

    WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), wxCpConfigStorage, timestamp, nonce, msgSignature);
    WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);
    if (outMessage != null) {
      response.getWriter().write(outMessage.toEncryptedXml(wxCpConfigStorage));
    }

    return;
  }

}

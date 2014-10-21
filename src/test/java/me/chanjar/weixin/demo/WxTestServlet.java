package me.chanjar.weixin.demo;

import me.chanjar.weixin.api.*;
import me.chanjar.weixin.bean.WxXmlMessage;
import me.chanjar.weixin.bean.WxXmlOutMessage;
import me.chanjar.weixin.bean.WxXmlOutTextMessage;
import org.apache.commons.lang3.StringUtils;

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
public class WxTestServlet extends HttpServlet {

  protected WxService wxService;
  protected WxConfigStorage wxConfigStorage;
  protected WxMessageRouter wxMessageRouter;

  @Override public void init() throws ServletException {
    //
    super.init();
    try {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxTestConfigStorage config = WxTestConfigStorage.fromXml(is1);

      wxConfigStorage = config;
      wxService = new WxServiceImpl();
      wxService.setWxConfigStorage(config);

      WxMessageHandler handler = new WxMessageHandler() {
        @Override public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {
          WxXmlOutTextMessage m
              = WxXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName())
              .toUser(wxMessage.getFromUserName()).build();
          return m;
        }
      };

      wxMessageRouter = new WxMessageRouter();
      wxMessageRouter
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

    String signature = request.getParameter("signature");
    String nonce = request.getParameter("nonce");
    String timestamp = request.getParameter("timestamp");

    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    if (!wxService.checkSignature(timestamp, nonce, signature)) {
      // 消息签名不正确，说明不是公众平台发过来的消息
      response.getWriter().println("非法请求");
      return;
    }

    String echostr = request.getParameter("echostr");
    if (StringUtils.isNotBlank(echostr)) {
      // 说明是一个仅仅用来验证的请求，回显echostr
      response.getWriter().println(echostr);
      return;
    }

    String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ?
        "raw" :
        request.getParameter("encrypt_type");

    WxXmlMessage inMessage = null;

    if ("raw".equals(encryptType)) {
      // 明文传输的消息
      inMessage = WxXmlMessage.fromXml(request.getInputStream());
    } else if ("aes".equals(encryptType)) {
      // 是aes加密的消息
      String msgSignature = request.getParameter("msg_signature");
      inMessage = WxXmlMessage.fromEncryptedXml(
          request.getInputStream(),
          wxConfigStorage,
          timestamp, nonce, msgSignature);
    } else {
      response.getWriter().println("不可识别的加密类型");
      return;
    }

    WxXmlOutMessage outMessage = wxMessageRouter.route(inMessage);

    if (outMessage != null) {
      if ("raw".equals(encryptType)) {
        response.getWriter().write(outMessage.toXml());
      } else if ("aes".equals(encryptType)) {
        response.getWriter().write(outMessage.toEncryptedXml(wxConfigStorage));
      }
      return;
    }

  }

}

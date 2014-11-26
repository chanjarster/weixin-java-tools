package me.chanjar.weixin.mp.demo;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.bean.WxMpMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

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
public class WxMpDemoServlet extends HttpServlet {

  protected WxMpConfigStorage wxMpConfigStorage;
  protected WxMpService wxMpService;
  protected WxMpMessageRouter wxMpMessageRouter;

  @Override public void init() throws ServletException {
    //
    super.init();
    try {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxMpDemoInMemoryConfigStorage config = WxMpDemoInMemoryConfigStorage.fromXml(is1);

      wxMpConfigStorage = config;
      wxMpService = new WxMpServiceImpl();
      wxMpService.setWxMpConfigStorage(config);

      WxMpMessageHandler textHandler = new WxMpMessageHandler() {
        @Override public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService) {
          WxMpXmlOutTextMessage m
              = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName())
              .toUser(wxMessage.getFromUserName()).build();
          return m;
        }
      };

      WxMpMessageHandler imageHandler = new WxMpMessageHandler() {
        @Override public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService) {
          try {
            WxMediaUploadResult wxMediaUploadResult = wxMpService
                .mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, ClassLoader.getSystemResourceAsStream("mm.jpeg"));
            WxMpMpXmlOutImageMessage m
                = WxMpXmlOutMessage
                .IMAGE()
                .mediaId(wxMediaUploadResult.getMediaId())
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName())
                .build();
            return m;
          } catch (WxErrorException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
          return null;
        }
      };

      wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
      wxMpMessageRouter
          .rule()
            .async(false)
            .content("哈哈") // 拦截内容为“哈哈”的消息
            .handler(textHandler)
          .end()
          .rule()
            .async(false)
            .content("图片")
            .handler(imageHandler)
          .end()
      ;

    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String signature = request.getParameter("signature");
    String nonce = request.getParameter("nonce");
    String timestamp = request.getParameter("timestamp");

    if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
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

    if ("raw".equals(encryptType)) {
      // 明文传输的消息
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
      WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
      response.getWriter().write(outMessage.toXml());
      return;
    }

    if ("aes".equals(encryptType)) {
      // 是aes加密的消息
      String msgSignature = request.getParameter("msg_signature");
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage, timestamp, nonce, msgSignature);
      WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
      response.getWriter().write(outMessage.toEncryptedXml(wxMpConfigStorage));
      return;
    }

    response.getWriter().println("不可识别的加密类型");
    return;
  }

}

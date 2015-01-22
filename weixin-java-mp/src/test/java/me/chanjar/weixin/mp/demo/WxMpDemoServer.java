package me.chanjar.weixin.mp.demo;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class WxMpDemoServer {

  private static WxMpConfigStorage wxMpConfigStorage;
  private static WxMpService wxMpService;
  private static WxMpMessageRouter wxMpMessageRouter;

  public static void main(String[] args) throws Exception {
    initWeixin();

    Server server = new Server(8080);

    ServletHandler servletHandler = new ServletHandler();
    server.setHandler(servletHandler);

    ServletHolder endpointServletHolder = new ServletHolder(new WxMpEndpointServlet(wxMpConfigStorage, wxMpService, wxMpMessageRouter));
    servletHandler.addServletWithMapping(endpointServletHolder, "/*");

    ServletHolder oauthServletHolder = new ServletHolder(new WxMpOAuth2Servlet(wxMpService));
    servletHandler.addServletWithMapping(oauthServletHolder, "/oauth2/*");

    server.start();
    server.join();
  }

  private static void initWeixin() {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxMpDemoInMemoryConfigStorage config = WxMpDemoInMemoryConfigStorage.fromXml(is1);

      wxMpConfigStorage = config;
      wxMpService = new WxMpServiceImpl();
      wxMpService.setWxMpConfigStorage(config);

      WxMpMessageHandler textHandler = new WxMpMessageHandler() {
        @Override
        public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
            WxMpService wxMpService, WxSessionManager sessionManager) {
          WxMpXmlOutTextMessage m
              = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName())
              .toUser(wxMessage.getFromUserName()).build();
          return m;
        }
      };

      WxMpMessageHandler imageHandler = new WxMpMessageHandler() {
        @Override
        public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
            WxMpService wxMpService, WxSessionManager sessionManager) {
          try {
            WxMediaUploadResult wxMediaUploadResult = wxMpService
                .mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, ClassLoader.getSystemResourceAsStream("mm.jpeg"));
            WxMpXmlOutImageMessage m
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

      WxMpMessageHandler oauth2handler = new WxMpMessageHandler() {
        @Override
        public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
            WxMpService wxMpService, WxSessionManager sessionManager) {
          String href = "<a href=\"" + wxMpService.oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_USER_INFO, null)
              + "\">测试oauth2</a>";
          return WxMpXmlOutMessage
              .TEXT()
              .content(href)
              .fromUser(wxMessage.getToUserName())
              .toUser(wxMessage.getFromUserName()).build();
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
          .rule()
          .async(false)
          .content("oauth")
          .handler(oauth2handler)
          .end()
      ;

  }
}

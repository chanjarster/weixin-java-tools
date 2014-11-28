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
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class WxMpOAuth2Servlet extends HttpServlet {

  protected WxMpService wxMpService;

  public WxMpOAuth2Servlet(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String code = request.getParameter("code");
    try {
      response.getWriter().println("<h1>code</h1>");
      response.getWriter().println(code);

      WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
      response.getWriter().println("<h1>access token</h1>");
      response.getWriter().println(wxMpOAuth2AccessToken.toString());

      WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
      response.getWriter().println("<h1>user info</h1>");
      response.getWriter().println(wxMpUser.toString());

      wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
      response.getWriter().println("<h1>after refresh</h1>");
      response.getWriter().println(wxMpOAuth2AccessToken.toString());

    } catch (WxErrorException e) {
      e.printStackTrace();
    }

    response.getWriter().flush();
    response.getWriter().close();

  }

}

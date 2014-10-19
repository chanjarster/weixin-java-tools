package me.chanjar.weixin.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * @author Daniel Qian
 */
public class WxTestServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);

    ServletHandler handler = new ServletHandler();
    server.setHandler(handler);

    handler.addServletWithMapping(WxTestServlet.class, "/*");
    server.start();
    server.join();
  }
}

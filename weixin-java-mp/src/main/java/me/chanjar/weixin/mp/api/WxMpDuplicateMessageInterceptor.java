package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import sun.applet.Main;

import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * 消息去重拦截器
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次
 * 使用方法：
 * WxMpMessageRouter router = new WxMpMessageRouter();
 * router
 *   .rule()
 *       .interceptor(new WxMpDuplicateMessageInterceptor())
 *   .next()
 *   .rule()
 *       .msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT")
 *       .interceptor(interceptor, ...).handler(handler, ...)
 *   .end()
 *   .rule()
 *       // 另外一个匹配规则
 *   .end()
 * ;
 * </pre>
 */
public class WxMpDuplicateMessageInterceptor implements WxMpMessageInterceptor {

  private static final Long PERIOD = 15 * 1000l;

  private final ConcurrentHashMap<Long, Long> msgId2timestamp;

  public WxMpDuplicateMessageInterceptor() {
    this.msgId2timestamp = new ConcurrentHashMap<Long, Long>();
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (true) {
            Thread.sleep(PERIOD);
            Long now = System.currentTimeMillis();
            for (Map.Entry<Long, Long> entry : msgId2timestamp.entrySet()) {
              if (now - entry.getValue() > PERIOD) {
                msgId2timestamp.entrySet().remove(entry);
              }
            }
            msgId2timestamp.clear();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    t.setDaemon(true);
    t.start();
  }

  @Override
  public boolean intercept(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService) {
    Long now = System.currentTimeMillis();
    Long timestamp = msgId2timestamp.putIfAbsent(wxMessage.getMsgId(), now);
    if (timestamp == null) {
      return true;
    }
    if (timestamp.equals(now)) {
      // 第一次接收到这个消息
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    WxMpDuplicateMessageInterceptor d = new WxMpDuplicateMessageInterceptor();
    Long endTime = System.currentTimeMillis() + 30 * 1000;
    Random r = new Random();

    while(System.currentTimeMillis() < endTime) {
      WxMpXmlMessage m = new WxMpXmlMessage();
      m.setMsgId(r.nextLong() % 100);
      d.intercept(m, null, null);
    }

  }
}

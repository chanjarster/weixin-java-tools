package me.chanjar.weixin.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * 默认消息重复检查器
 * 将每个消息id保存在内存里，每隔5秒清理已经过期的消息id，每个消息id的过期时间是15秒
 * </pre>
 */
public class WxMsgIdInMemoryDuplicateChecker implements WxMsgIdDuplicateChecker {

  /**
   * 一个消息ID在内存的过期时间：15秒
   */
  private final Long TIME_TO_LIVE;

  /**
   * 每隔多少周期检查消息ID是否过期：5秒
   */
  private final Long CLEAR_PERIOD;

  private final ConcurrentHashMap<Long, Long> msgId2Timestamp = new ConcurrentHashMap<Long, Long>();

  /**
   * WxMsgIdInMemoryDuplicateChecker构造函数
   * <pre>
   * 一个消息ID在内存的过期时间：15秒
   * 每隔多少周期检查消息ID是否过期：5秒
   * </pre>
   */
  public WxMsgIdInMemoryDuplicateChecker() {
    this.TIME_TO_LIVE = 15 * 1000l;
    this.CLEAR_PERIOD = 5 * 1000l;
    this.start();
  }

  /**
   * WxMsgIdInMemoryDuplicateChecker构造函数
   * @param timeToLive 一个消息ID在内存的过期时间：毫秒
   * @param clearPeriod 每隔多少周期检查消息ID是否过期：毫秒
   */
  public WxMsgIdInMemoryDuplicateChecker(Long timeToLive, Long clearPeriod) {
    this.TIME_TO_LIVE = timeToLive;
    this.CLEAR_PERIOD = clearPeriod;
    this.start();
  }

  private void start() {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (true) {
            Thread.sleep(CLEAR_PERIOD);
            Long now = System.currentTimeMillis();
            for (Map.Entry<Long, Long> entry : msgId2Timestamp.entrySet()) {
              if (now - entry.getValue() > TIME_TO_LIVE) {
                msgId2Timestamp.entrySet().remove(entry);
              }
            }
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
  public boolean isDuplicate(Long wxMsgId) {
    Long timestamp = msgId2Timestamp.putIfAbsent(wxMsgId, System.currentTimeMillis());
    if (timestamp == null) {
      // 第一次接收到这个消息
      return false;
    }
    return true;
  }


}

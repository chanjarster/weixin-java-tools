package me.chanjar.weixin.common.util;

/**
 * <pre>
 * 消息重复检查器
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次
 * </pre>
 */
public interface WxMessageDuplicateChecker {

  /**
   * 检查消息ID是否重复
   * @param wxMsgId
   * @return 如果是重复消息，返回true，否则返回false
   */
  public boolean isDuplicate(Long wxMsgId);

}

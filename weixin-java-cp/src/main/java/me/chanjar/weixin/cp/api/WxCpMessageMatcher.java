package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxCpMessageMatcher {

  /**
   * 消息是否匹配某种模式
   * @param message
   * @return
   */
  public boolean match(WxCpXmlMessage message);

}

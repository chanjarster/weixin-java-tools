package me.chanjar.weixin.enterprise.api;

import java.util.Map;

import me.chanjar.weixin.enterprise.bean.WxCpXmlMessage;
import me.chanjar.weixin.enterprise.bean.WxXmlOutMessage;

/**
 * 处理微信推送消息的处理器接口
 * @author Daniel Qian
 *
 */
public interface WxCpMessageHandler {

  /**
   * 
   * @param wxMessage
   * @param context  上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
   */
  public WxXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context);
  
}

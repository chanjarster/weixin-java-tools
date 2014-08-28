package chanjarster.weixin.api;

import java.util.Map;

import chanjarster.weixin.bean.WxXmlMessage;
import chanjarster.weixin.bean.WxXmlOutMessage;

/**
 * 处理微信推送消息的处理器接口
 * @author chanjarster
 *
 */
public interface WxMessageHandler {

  /**
   * 
   * @param wxMessage
   * @param context  上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
   */
  public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context);
  
}

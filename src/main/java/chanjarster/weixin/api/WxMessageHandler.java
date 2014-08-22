package chanjarster.weixin.api;

import java.util.Map;

import chanjarster.weixin.bean.WxXmlMessage;

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
   */
  public void handle(WxXmlMessage wxMessage, Map<String, Object> context);
  
}

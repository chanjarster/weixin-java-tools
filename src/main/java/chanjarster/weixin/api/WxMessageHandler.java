package chanjarster.weixin.api;

import java.util.Map;

import chanjarster.weixin.bean.WxXmlMessage;

/**
 * 处理微信推送消息的处理器
 * @author chanjarster
 *
 */
public interface WxMessageHandler {

  public void handle(WxXmlMessage wxMessage, Map<String, Object> context);
  
}

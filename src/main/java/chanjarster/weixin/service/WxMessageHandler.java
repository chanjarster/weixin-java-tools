package chanjarster.weixin.service;

import java.util.Map;

import chanjarster.weixin.out.WxUserMessage;

/**
 * 处理微信推送消息的处理器
 * @author chanjarster
 *
 */
public interface WxMessageHandler {

  public void handle(WxUserMessage wxMessage, Map<String, Object> context);
  
}

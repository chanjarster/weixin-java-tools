package chanjarster.weixin.service;

import java.util.Map;

import chanjarster.weixin.out.WxUserMessage;


/**
 * 微信消息拦截器，可以用来做一些验证
 * @author qianjia
 *
 */
public interface WxMessageInterceptor {

  /**
   * 拦截微信消息
   * @param wxMessage
   * @return  true代表OK，false代表不OK
   */
  public boolean intercept(WxUserMessage wxMessage, Map<String, Object> context);
  
}

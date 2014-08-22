package chanjarster.weixin.api;

import java.util.Map;

import chanjarster.weixin.bean.WxXmlMessage;


/**
 * 微信消息拦截器，可以用来做验证
 * @author qianjia
 *
 */
public interface WxMessageInterceptor {

  /**
   * 拦截微信消息
   * @param wxMessage
   * @return  true代表OK，false代表不OK
   */
  public boolean intercept(WxXmlMessage wxMessage, Map<String, Object> context);
  
}

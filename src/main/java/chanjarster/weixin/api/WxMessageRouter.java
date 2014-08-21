package chanjarster.weixin.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chanjarster.weixin.api.WxMessageRouterTest.WxEchoMessageHandler;
import chanjarster.weixin.bean.WxXmlMessage;

/**
 * <pre>
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给handler处理
 * 
 * 使用方法：
 * WxMessageRouter router = new WxMessageRouter();
 * router
 *   .rule()
 *       .msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT")
 *       .interceptor(interceptor, ...).handler(handler, ...)
 *   .end()
 *   .rule()
 *       // 另外一个匹配规则
 *   .end()
 * ;
 * 
 * // 将WxXmlMessage交给消息路由器
 * router.route(message);
 * 
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link Rule#reEnter()}
 * </pre>
 * @author qianjia
 *
 */
public class WxMessageRouter {
  
  private List<Rule> rules = new ArrayList<Rule>();

  /**
   * 开始一个新的Route规则
   * @return
   */
  public Rule rule() {
    return new Rule(this);
  }

  /**
   * 处理微信消息
   * @param wxMessage
   */
  public void route(WxXmlMessage wxMessage) {
    for (Rule rule : rules) {
      boolean doNext = rule.service(wxMessage);
      if (!doNext) {
        break;
      }
    }
  }
  
  public static class Rule {
    
    private final WxMessageRouter routerBuilder;

    private String msgType;

    private String event;
    
    private String eventKey;
    
    private String content;
    
    private boolean reEnter = false;
    
    private List<WxMessageHandler> handlers = new ArrayList<WxMessageHandler>();
    
    private List<WxMessageInterceptor> interceptors = new ArrayList<WxMessageInterceptor>();
    
    protected Rule(WxMessageRouter routerBuilder) {
      this.routerBuilder = routerBuilder;
    }
    
    /**
     * 如果msgType等于某值
     * @param msgType
     * @return
     */
    public Rule msgType(String msgType) {
      this.msgType = msgType;
      return this;
    }
    
    /**
     * 如果event等于某值
     * @param event
     * @return
     */
    public Rule event(String event) {
      this.event = event;
      return this;
    }
    
    /**
     * 如果eventKey等于某值
     * @param eventKey
     * @return
     */
    public Rule eventKey(String eventKey) {
      this.eventKey = eventKey;
      return this;
    }
    
    /**
     * 如果content等于某值
     * @param content
     * @return
     */
    public Rule content(String content) {
      this.content = content;
      return this;
    }
    
    /**
     * 将消息交给后面的Rule处理
     * @return
     */
    public Rule reEnter() {
      this.reEnter = true;
      return this;
    }
    
    /**
     * 添加interceptor
     * @param interceptor
     * @param otherInterceptors
     * @return
     */
    public Rule interceptor(WxMessageInterceptor interceptor, WxMessageInterceptor... otherInterceptors) {
      this.interceptors.add(interceptor);
      if (otherInterceptors != null && otherInterceptors.length > 0) {
        for (WxMessageInterceptor i : otherInterceptors) {
          this.interceptors.add(i);
        }
      }
      return this;
    }
    
    /**
     * 添加handler
     * @param handler
     * @return
     */
    public Rule handler(WxMessageHandler handler, WxMessageHandler... otherHandlers) {
      this.handlers.add(handler);
      if (otherHandlers != null && otherHandlers.length > 0) {
        for (WxMessageHandler i : otherHandlers) {
          this.handlers.add(i);
        }
      }
      return this;
    }
    
    /**
     * 规则结束
     * @return
     */
    public WxMessageRouter end() {
      this.routerBuilder.rules.add(this);
      return this.routerBuilder;
    }
    
    protected boolean test(WxXmlMessage wxMessage) {
      return 
          (this.msgType == null || this.msgType.equals(wxMessage.getMsgType()))
          &&
          (this.event == null || this.event.equals(wxMessage.getEvent()))
          &&
          (this.eventKey == null || this.eventKey.equals(wxMessage.getEventKey()))
          &&
          (this.content == null || this.content.equals(wxMessage.getContent() == null ? null : wxMessage.getContent().trim()))
      ;
    }
    
    /**
     * 处理微信推送过来的消息
     * @param wxMessage
     * @return true 代表继续执行别的router，false 代表停止执行别的router
     */
    protected boolean service(WxXmlMessage wxMessage) {
      // 如果不匹配本规则，那么接着执行后面的Rule
      if (!test(wxMessage)) {
        return true;
      }
      
      Map<String, Object> context = new HashMap<String, Object>();
      // 如果拦截器不通过
      for (WxMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context)) {
          return this.reEnter;
        }
      }
      
      // 交给handler处理
      for (WxMessageHandler interceptor : this.handlers) {
        interceptor.handle(wxMessage, context);
      }
      
      return this.reEnter;
    }
    
  }
  
}

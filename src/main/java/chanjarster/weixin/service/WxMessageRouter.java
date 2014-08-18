package chanjarster.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chanjarster.weixin.out.WxUserMessage;

/**
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给某个的handler处理
 * @author qianjia
 *
 */
public class WxMessageRouter {
  
  private List<Rule> rules = new ArrayList<Rule>();

  /**
   * 开始一个新的Route规则
   * @return
   */
  public Rule start() {
    return new Rule(this);
  }

  /**
   * 处理微信消息
   * @param wxMessage
   */
  public void route(WxUserMessage wxMessage) {
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
    
    private boolean forward = false;
    
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
     * 如果本规则命中，在执行完handler后，还会接着给后面的Rule执行
     * @return
     */
    public Rule forward() {
      this.forward = true;
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
    
    protected boolean test(WxUserMessage wxMessage) {
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
    protected boolean service(WxUserMessage wxMessage) {
      // 如果不匹配本规则，那么接着执行后面的Rule
      if (!test(wxMessage)) {
        return true;
      }
      
      Map<String, Object> context = new HashMap<String, Object>();
      // 如果拦截器不通过
      for (WxMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context)) {
          return this.forward;
        }
      }
      
      // 交给handler处理
      for (WxMessageHandler interceptor : this.handlers) {
        interceptor.handle(wxMessage, context);
      }
      
      return this.forward;
    }
    
  }
  
}

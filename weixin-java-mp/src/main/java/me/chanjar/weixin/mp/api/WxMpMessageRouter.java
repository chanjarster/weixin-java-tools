package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * <pre>
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给handler处理
 * 
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link Rule#next()}
 * 3. 规则的结束必须用{@link Rule#end()}或者{@link Rule#next()}，否则不会生效
 * 
 * 使用方法：
 * WxMpMessageRouter router = new WxMpMessageRouter();
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
 * </pre>
 * @author qianjia
 *
 */
public class WxMpMessageRouter {

  private static final int DEFAULT_THREAD_POOL_SIZE = 20;

  private final List<Rule> rules = new ArrayList<Rule>();

  private final ExecutorService executorService;

  private final WxMpService wxMpService;

  public WxMpMessageRouter(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
    this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
  }

  public WxMpMessageRouter(WxMpService wxMpService, int threadPoolSize) {
    this.wxMpService = wxMpService;
    this.executorService = Executors.newFixedThreadPool(threadPoolSize);
  }

  /**
   * 开始一个新的Route规则
   * @return
   */
  public Rule rule() {
    return new Rule(this, wxMpService);
  }

  /**
   * 处理微信消息
   * @param wxMessage
   */
  public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage) {
    final List<Rule> matchRules = new ArrayList<Rule>();
    // 收集匹配的规则
    for (final Rule rule : rules) {
      if (rule.test(wxMessage)) {
        matchRules.add(rule);
      }
    }
    
    if (matchRules.size() == 0) {
      return null;
    }
    
    if (matchRules.get(0).async) {
      // 只要第一个是异步的，那就异步执行
      // 在另一个线程里执行
      executorService.execute(new Runnable() {
        public void run() {
          for (final Rule rule : matchRules) {
            rule.service(wxMessage);
            if (!rule.reEnter) {
              break;
            }
          }
        }
      });
      return null;
    }
    
    WxMpXmlOutMessage res = null;
    for (final Rule rule : matchRules) {
      // 返回最后一个匹配规则的结果
      res = rule.service(wxMessage);
      if (!rule.reEnter) {
        break;
      }
    }
    return res;
  }
  
  public static class Rule {
    
    private final WxMpMessageRouter routerBuilder;

    private final WxMpService wxMpService;

    private boolean async = true;

    private String fromUser;
    
    private String msgType;

    private String event;
    
    private String eventKey;
    
    private String content;
    
    private String rContent;
    
    private boolean reEnter = false;
    
    private List<WxMpMessageHandler> handlers = new ArrayList<WxMpMessageHandler>();
    
    private List<WxMpMessageInterceptor> interceptors = new ArrayList<WxMpMessageInterceptor>();
    
    protected Rule(WxMpMessageRouter routerBuilder, WxMpService wxMpService) {
      this.routerBuilder = routerBuilder;
      this.wxMpService = wxMpService;
    }
    
    /**
     * 设置是否异步执行，默认是true
     * @param async
     * @return
     */
    public Rule async(boolean async) {
      this.async = async;
      return this;
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
     * 如果content匹配该正则表达式
     * @param regex
     * @return
     */
    public Rule rContent(String regex) {
      this.rContent = regex;
      return this;
    }

    /**
     * 如果fromUser等于某值
     * @param fromUser
     * @return
     */
    public Rule fromUser(String fromUser) {
      this.fromUser = fromUser;
      return this;
    }

    /**
     * 设置微信消息拦截器
     * @param interceptor
     * @return
     */
    public Rule interceptor(WxMpMessageInterceptor interceptor) {
      return interceptor(interceptor, (WxMpMessageInterceptor[]) null);
    }
    
    /**
     * 设置微信消息拦截器
     * @param interceptor
     * @param otherInterceptors
     * @return
     */
    public Rule interceptor(WxMpMessageInterceptor interceptor, WxMpMessageInterceptor... otherInterceptors) {
      this.interceptors.add(interceptor);
      if (otherInterceptors != null && otherInterceptors.length > 0) {
        for (WxMpMessageInterceptor i : otherInterceptors) {
          this.interceptors.add(i);
        }
      }
      return this;
    }
    
    /**
     * 设置微信消息处理器
     * @param handler
     * @return
     */
    public Rule handler(WxMpMessageHandler handler) {
      return handler(handler, (WxMpMessageHandler[]) null);
    }
    
    /**
     * 设置微信消息处理器
     * @param handler
     * @param otherHandlers
     * @return
     */
    public Rule handler(WxMpMessageHandler handler, WxMpMessageHandler... otherHandlers) {
      this.handlers.add(handler);
      if (otherHandlers != null && otherHandlers.length > 0) {
        for (WxMpMessageHandler i : otherHandlers) {
          this.handlers.add(i);
        }
      }
      return this;
    }
    
    /**
     * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
     * @return
     */
    public WxMpMessageRouter end() {
      this.routerBuilder.rules.add(this);
      return this.routerBuilder;
    }
    
    /**
     * 规则结束，但是消息还会进入其他规则
     * @return
     */
    public WxMpMessageRouter next() {
      this.reEnter = true;
      return end();
    }
    
    protected boolean test(WxMpXmlMessage wxMessage) {
      return
          (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUserName()))
          &&
          (this.msgType == null || this.msgType.equals(wxMessage.getMsgType()))
          &&
          (this.event == null || this.event.equals(wxMessage.getEvent()))
          &&
          (this.eventKey == null || this.eventKey.equals(wxMessage.getEventKey()))
          &&
          (this.content == null || this.content.equals(wxMessage.getContent() == null ? null : wxMessage.getContent().trim()))
          &&
          (this.rContent == null || Pattern.matches(this.rContent, wxMessage.getContent() == null ? "" : wxMessage.getContent().trim()))
      ;
    }
    
    /**
     * 处理微信推送过来的消息
     * @param wxMessage
     * @return true 代表继续执行别的router，false 代表停止执行别的router
     */
    protected WxMpXmlOutMessage service(WxMpXmlMessage wxMessage) {
      Map<String, Object> context = new HashMap<String, Object>();
      // 如果拦截器不通过
      for (WxMpMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context, wxMpService)) {
          return null;
        }
      }
      
      // 交给handler处理
      WxMpXmlOutMessage res = null;
      for (WxMpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        res = handler.handle(wxMessage, context, wxMpService);
      }
      return res;
    }
    
  }
  
}

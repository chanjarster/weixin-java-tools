package me.chanjar.weixin.cp.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

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
 * WxCpMessageRouter router = new WxCpMessageRouter();
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
 * @author Daniel Qian
 *
 */
public class WxCpMessageRouter {

  private static final int DEFAULT_THREAD_POOL_SIZE = 20;

  private final List<Rule> rules = new ArrayList<Rule>();

  private final ExecutorService executorService;

  private final WxCpService wxCpService;

  public WxCpMessageRouter(WxCpService wxCpService) {
    this.wxCpService = wxCpService;
    this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
  }

  public WxCpMessageRouter(WxCpService wxMpService, int threadPoolSize) {
    this.wxCpService = wxMpService;
    this.executorService = Executors.newFixedThreadPool(threadPoolSize);
  }

  /**
   * 开始一个新的Route规则
   * @return
   */
  public Rule rule() {
    return new Rule(this, wxCpService);
  }

  /**
   * 处理微信消息
   * @param wxMessage
   */
  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage) {
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
      executorService.submit(new Runnable() {
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

    WxCpXmlOutMessage res = null;
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

    private final WxCpMessageRouter routerBuilder;

    private final WxCpService wxCpService;

    private boolean async = true;

    private String fromUser;

    private String msgType;

    private String event;

    private String eventKey;

    private String content;

    private String rContent;

    private boolean reEnter = false;

    private Integer agentId;

    private List<WxCpMessageHandler> handlers = new ArrayList<WxCpMessageHandler>();

    private List<WxCpMessageInterceptor> interceptors = new ArrayList<WxCpMessageInterceptor>();

    protected Rule(WxCpMessageRouter routerBuilder, WxCpService wxCpService) {
      this.routerBuilder = routerBuilder;
      this.wxCpService = wxCpService;
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
     * 如果agentId匹配
     * @param agentId
     * @return
     */
    public Rule agentId(Integer agentId) {
      this.agentId = agentId;
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
    public Rule interceptor(WxCpMessageInterceptor interceptor) {
      return interceptor(interceptor, (WxCpMessageInterceptor[]) null);
    }

    /**
     * 设置微信消息拦截器
     * @param interceptor
     * @param otherInterceptors
     * @return
     */
    public Rule interceptor(WxCpMessageInterceptor interceptor, WxCpMessageInterceptor... otherInterceptors) {
      this.interceptors.add(interceptor);
      if (otherInterceptors != null && otherInterceptors.length > 0) {
        for (WxCpMessageInterceptor i : otherInterceptors) {
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
    public Rule handler(WxCpMessageHandler handler) {
      return handler(handler, (WxCpMessageHandler[]) null);
    }

    /**
     * 设置微信消息处理器
     * @param handler
     * @param otherHandlers
     * @return
     */
    public Rule handler(WxCpMessageHandler handler, WxCpMessageHandler... otherHandlers) {
      this.handlers.add(handler);
      if (otherHandlers != null && otherHandlers.length > 0) {
        for (WxCpMessageHandler i : otherHandlers) {
          this.handlers.add(i);
        }
      }
      return this;
    }

    /**
     * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
     * @return
     */
    public WxCpMessageRouter end() {
      this.routerBuilder.rules.add(this);
      return this.routerBuilder;
    }

    /**
     * 规则结束，但是消息还会进入其他规则
     * @return
     */
    public WxCpMessageRouter next() {
      this.reEnter = true;
      return end();
    }

    protected boolean test(WxCpXmlMessage wxMessage) {
      return
          (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUserName()))
          &&
          (this.agentId == null || this.agentId.equals(wxMessage.getAgentId()))
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
    protected WxCpXmlOutMessage service(WxCpXmlMessage wxMessage) {
      Map<String, Object> context = new HashMap<String, Object>();
      // 如果拦截器不通过
      for (WxCpMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context, wxCpService)) {
          return null;
        }
      }

      // 交给handler处理
      WxCpXmlOutMessage res = null;
      for (WxCpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        res = handler.handle(wxMessage, context, wxCpService);
      }
      return res;
    }

  }

}

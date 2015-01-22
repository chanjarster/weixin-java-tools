package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.session.*;
import me.chanjar.weixin.common.util.WxMessageDuplicateChecker;
import me.chanjar.weixin.common.util.WxMessageInMemoryDuplicateChecker;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

  protected final Logger log = LoggerFactory.getLogger(WxCpMessageRouter.class);

  private static final int DEFAULT_THREAD_POOL_SIZE = 100;

  private final List<Rule> rules = new ArrayList<Rule>();

  private final WxCpService wxCpService;

  private ExecutorService executorService;

  private WxMessageDuplicateChecker messageDuplicateChecker;

  private WxSessionManager sessionManager;

  public WxCpMessageRouter(WxCpService wxCpService) {
    this.wxCpService = wxCpService;
    this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    this.messageDuplicateChecker = new WxMessageInMemoryDuplicateChecker();
    this.sessionManager = new StandardSessionManager();
  }

  /**
   * <pre>
   * 设置自定义的 {@link ExecutorService}
   * 如果不调用该方法，默认使用 Executors.newFixedThreadPool(100)
   * </pre>
   * @param executorService
   */
  public void setExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * <pre>
   * 设置自定义的 {@link me.chanjar.weixin.common.util.WxMessageDuplicateChecker}
   * 如果不调用该方法，默认使用 {@link me.chanjar.weixin.common.util.WxMessageInMemoryDuplicateChecker}
   * </pre>
   * @param messageDuplicateChecker
   */
  public void setMessageDuplicateChecker(WxMessageDuplicateChecker messageDuplicateChecker) {
    this.messageDuplicateChecker = messageDuplicateChecker;
  }

  /**
   * <pre>
   * 设置自定义的{@link me.chanjar.weixin.common.session.WxSessionManager}
   * 如果不调用该方法，默认使用 {@linke SessionManagerImpl}
   * </pre>
   * @param sessionManager
   */
  public void setSessionManager(WxSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  /**
   * 开始一个新的Route规则
   * @return
   */
  public Rule rule() {
    return new Rule(this, wxCpService, sessionManager);
  }

  /**
   * 处理微信消息
   * @param wxMessage
   */
  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage) {
    if (isDuplicateMessage(wxMessage)) {
      // 如果是重复消息，那么就不做处理
      return null;
    }
    
    final List<Rule> matchRules = new ArrayList<Rule>();
    // 收集匹配的规则
    for (final Rule rule : rules) {
      if (rule.test(wxMessage)) {
        matchRules.add(rule);
        if(!rule.reEnter) {
          break;
        }
      }
    }

    if (matchRules.size() == 0) {
      return null;
    }

    WxCpXmlOutMessage res = null;
    final List<Future> futures = new ArrayList<Future>();
    for (final Rule rule : matchRules) {
      // 返回最后一个非异步的rule的执行结果
      if(rule.async) {
        futures.add(
            executorService.submit(new Runnable() {
              public void run() {
                rule.service(wxMessage);
              }
            })
        );
      } else {
        res = rule.service(wxMessage);
        // 在同步操作结束，session访问结束
        log.debug("End session access: async=false, sessionId={}", wxMessage.getFromUserName());
        sessionEndAccess(wxMessage);
      }
    }

    if (futures.size() > 0) {
      executorService.submit(new Runnable() {
        @Override
        public void run() {
          for (Future future : futures) {
            try {
              future.get();
              log.debug("End session access: async=true, sessionId={}", wxMessage.getFromUserName());
              // 异步操作结束，session访问结束
              sessionEndAccess(wxMessage);
            } catch (InterruptedException e) {
              log.error("Error happened when wait task finish", e);
            } catch (ExecutionException e) {
              log.error("Error happened when wait task finish", e);
            }
          }
        }
      });
    }
    return res;
  }

  protected boolean isDuplicateMessage(WxCpXmlMessage wxMessage) {

    String messageId = "";
    if (wxMessage.getMsgId() == null) {
      messageId = wxMessage.getFromUserName() + "-" + String.valueOf(wxMessage.getCreateTime());
    } else {
      messageId = String.valueOf(wxMessage.getMsgId());
    }

    if (messageDuplicateChecker.isDuplicate(messageId)) {
      return true;
    }
    return false;

  }

  /**
   * 对session的访问结束
   * @param wxMessage
   */
  protected void sessionEndAccess(WxCpXmlMessage wxMessage) {

    InternalSession session = ((InternalSessionManager)sessionManager).findSession(wxMessage.getFromUserName());
    if (session != null) {
      session.endAccess();
    }

  }

  public static class Rule {

    private final WxCpMessageRouter routerBuilder;

    private final WxCpService wxCpService;

    private final WxSessionManager sessionManager;

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

    protected Rule(WxCpMessageRouter routerBuilder, WxCpService wxCpService, WxSessionManager sessionManager) {
      this.routerBuilder = routerBuilder;
      this.wxCpService = wxCpService;
      this.sessionManager = sessionManager;
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
        if (!interceptor.intercept(wxMessage, context, wxCpService, sessionManager)) {
          return null;
        }
      }

      // 交给handler处理
      WxCpXmlOutMessage res = null;
      for (WxCpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        res = handler.handle(wxMessage, context, wxCpService, sessionManager);
      }
      return res;
    }

  }

}

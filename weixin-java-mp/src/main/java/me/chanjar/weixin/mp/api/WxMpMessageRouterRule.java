package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.api.WxErrorExceptionHandler;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class WxMpMessageRouterRule {

  private final WxMpMessageRouter routerBuilder;

  private boolean async = true;

  private String fromUser;

  private String msgType;

  private String event;

  private String eventKey;

  private String content;

  private String rContent;

  private WxMpMessageMatcher matcher;

  private boolean reEnter = false;

  private List<WxMpMessageHandler> handlers = new ArrayList<WxMpMessageHandler>();

  private List<WxMpMessageInterceptor> interceptors = new ArrayList<WxMpMessageInterceptor>();

  public WxMpMessageRouterRule(WxMpMessageRouter routerBuilder) {
    this.routerBuilder = routerBuilder;
  }

  /**
   * 设置是否异步执行，默认是true
   *
   * @param async
   * @return
   */
  public WxMpMessageRouterRule async(boolean async) {
    this.async = async;
    return this;
  }

  /**
   * 如果msgType等于某值
   *
   * @param msgType
   * @return
   */
  public WxMpMessageRouterRule msgType(String msgType) {
    this.msgType = msgType;
    return this;
  }

  /**
   * 如果event等于某值
   *
   * @param event
   * @return
   */
  public WxMpMessageRouterRule event(String event) {
    this.event = event;
    return this;
  }

  /**
   * 如果eventKey等于某值
   *
   * @param eventKey
   * @return
   */
  public WxMpMessageRouterRule eventKey(String eventKey) {
    this.eventKey = eventKey;
    return this;
  }

  /**
   * 如果content等于某值
   *
   * @param content
   * @return
   */
  public WxMpMessageRouterRule content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 如果content匹配该正则表达式
   *
   * @param regex
   * @return
   */
  public WxMpMessageRouterRule rContent(String regex) {
    this.rContent = regex;
    return this;
  }

  /**
   * 如果fromUser等于某值
   *
   * @param fromUser
   * @return
   */
  public WxMpMessageRouterRule fromUser(String fromUser) {
    this.fromUser = fromUser;
    return this;
  }

  /**
   * 如果消息匹配某个matcher，用在用户需要自定义更复杂的匹配规则的时候
   *
   * @param matcher
   * @return
   */
  public WxMpMessageRouterRule matcher(WxMpMessageMatcher matcher) {
    this.matcher = matcher;
    return this;
  }

  /**
   * 设置微信消息拦截器
   *
   * @param interceptor
   * @return
   */
  public WxMpMessageRouterRule interceptor(WxMpMessageInterceptor interceptor) {
    return interceptor(interceptor, (WxMpMessageInterceptor[]) null);
  }

  /**
   * 设置微信消息拦截器
   *
   * @param interceptor
   * @param otherInterceptors
   * @return
   */
  public WxMpMessageRouterRule interceptor(WxMpMessageInterceptor interceptor, WxMpMessageInterceptor... otherInterceptors) {
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
   *
   * @param handler
   * @return
   */
  public WxMpMessageRouterRule handler(WxMpMessageHandler handler) {
    return handler(handler, (WxMpMessageHandler[]) null);
  }

  /**
   * 设置微信消息处理器
   *
   * @param handler
   * @param otherHandlers
   * @return
   */
  public WxMpMessageRouterRule handler(WxMpMessageHandler handler, WxMpMessageHandler... otherHandlers) {
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
   *
   * @return
   */
  public WxMpMessageRouter end() {
    this.routerBuilder.getRules().add(this);
    return this.routerBuilder;
  }

  /**
   * 规则结束，但是消息还会进入其他规则
   *
   * @return
   */
  public WxMpMessageRouter next() {
    this.reEnter = true;
    return end();
  }

  /**
   * 将微信自定义的事件修正为不区分大小写,
   * 比如框架定义的事件常量为click，但微信传递过来的却是CLICK
   * @param wxMessage
   * @return
   */
  protected boolean test(WxMpXmlMessage wxMessage) {
    return
        (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUserName()))
            &&
            (this.msgType == null || this.msgType.toLowerCase().equals((wxMessage.getMsgType()==null?null:wxMessage.getMsgType().toLowerCase())))
            &&
            (this.event == null || this.event.toLowerCase().equals((wxMessage.getEvent()==null?null:wxMessage.getEvent().toLowerCase())))
            &&
            (this.eventKey == null || this.eventKey.toLowerCase().equals((wxMessage.getEventKey()==null?null:wxMessage.getEventKey().toLowerCase())))
            &&
            (this.content == null || this.content
                .equals(wxMessage.getContent() == null ? null : wxMessage.getContent().trim()))
            &&
            (this.rContent == null || Pattern
                .matches(this.rContent, wxMessage.getContent() == null ? "" : wxMessage.getContent().trim()))
            &&
            (this.matcher == null || this.matcher.match(wxMessage))
        ;
  }

  /**
   * 处理微信推送过来的消息
   *
   * @param wxMessage
   * @return true 代表继续执行别的router，false 代表停止执行别的router
   */
  protected WxMpXmlOutMessage service(WxMpXmlMessage wxMessage,
      WxMpService wxMpService,
      WxSessionManager sessionManager,
      WxErrorExceptionHandler exceptionHandler) {

    try {

      Map<String, Object> context = new HashMap<String, Object>();
      // 如果拦截器不通过
      for (WxMpMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context, wxMpService, sessionManager)) {
          return null;
        }
      }

      // 交给handler处理
      WxMpXmlOutMessage res = null;
      for (WxMpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        res = handler.handle(wxMessage, context, wxMpService, sessionManager);
      }
      return res;
    } catch (WxErrorException e) {
      exceptionHandler.handle(e);
    }
    return null;

  }

  public WxMpMessageRouter getRouterBuilder() {
    return routerBuilder;
  }

  public boolean isAsync() {
    return async;
  }

  public void setAsync(boolean async) {
    this.async = async;
  }

  public String getFromUser() {
    return fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getMsgType() {
    return msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getrContent() {
    return rContent;
  }

  public void setrContent(String rContent) {
    this.rContent = rContent;
  }

  public WxMpMessageMatcher getMatcher() {
    return matcher;
  }

  public void setMatcher(WxMpMessageMatcher matcher) {
    this.matcher = matcher;
  }

  public boolean isReEnter() {
    return reEnter;
  }

  public void setReEnter(boolean reEnter) {
    this.reEnter = reEnter;
  }

  public List<WxMpMessageHandler> getHandlers() {
    return handlers;
  }

  public void setHandlers(List<WxMpMessageHandler> handlers) {
    this.handlers = handlers;
  }

  public List<WxMpMessageInterceptor> getInterceptors() {
    return interceptors;
  }

  public void setInterceptors(List<WxMpMessageInterceptor> interceptors) {
    this.interceptors = interceptors;
  }
}

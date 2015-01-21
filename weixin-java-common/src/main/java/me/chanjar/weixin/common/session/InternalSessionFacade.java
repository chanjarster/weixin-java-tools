package me.chanjar.weixin.common.session;

import java.util.Enumeration;

/**
 * Created by qianjia on 15/1/21.
 */
public class InternalSessionFacade implements WxSession {

  /**
   * Wrapped session object.
   */
  private WxSession session = null;

  public InternalSessionFacade(WxSession session) {
    session = session;
  }

  @Override
  public Object getAttribute(String name) {
    return session.getAttribute(name);
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return session.getAttributeNames();
  }

  @Override
  public void setAttribute(String name, Object value) {
    session.setAttribute(name, value);
  }

  @Override
  public void removeAttribute(String name) {
    session.removeAttribute(name);
  }

  @Override
  public void invalidate() {
    session.invalidate();
  }
}

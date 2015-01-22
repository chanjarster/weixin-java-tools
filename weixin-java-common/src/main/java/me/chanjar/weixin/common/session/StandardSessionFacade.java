package me.chanjar.weixin.common.session;

import java.util.Enumeration;

public class StandardSessionFacade implements WxSession {

  /**
   * Wrapped session object.
   */
  private WxSession session = null;

  public StandardSessionFacade(StandardSession session) {
    this.session = session;
  }

  public InternalSession getInternalSession() {
    return (InternalSession) session;
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

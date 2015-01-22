package me.chanjar.weixin.common.session;

import java.util.Enumeration;

public interface WxSession {

  public Object getAttribute(String name);

  public Enumeration<String> getAttributeNames();

  public void setAttribute(String name, Object value);

  public void removeAttribute(String name);

  public void invalidate();

}

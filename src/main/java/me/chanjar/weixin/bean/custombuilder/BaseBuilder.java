package me.chanjar.weixin.bean.custombuilder;

import me.chanjar.weixin.bean.WxCustomMessage;

public class BaseBuilder<T> {
  protected String msgType;
  protected String toUser;

  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = new WxCustomMessage();
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    return m;
  }
}

package chanjarster.weixin.bean.custombuilder;

import chanjarster.weixin.bean.WxCustomMessage;

public class BaseBuilder<T> {
  protected String msgtype;
  protected String touser;

  public T touser(String touser) {
    this.touser = touser;
    return (T) this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = new WxCustomMessage();
    m.setMsgtype(this.msgtype);
    m.setTouser(this.touser);
    return m;
  }
}

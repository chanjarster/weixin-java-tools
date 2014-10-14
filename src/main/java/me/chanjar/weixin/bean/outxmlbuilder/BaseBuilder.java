package me.chanjar.weixin.bean.outxmlbuilder;

import me.chanjar.weixin.bean.WxXmlOutMessage;


public abstract class BaseBuilder<BuilderType, ValueType> {
  
  protected String toUserName;
  
  protected String fromUserName;
  
  public BuilderType touser(String touser) {
    this.toUserName = touser;
    return (BuilderType) this;
  }
  
  public BuilderType fromUser(String fromusername) {
    this.fromUserName = fromusername;
    return (BuilderType) this;
  }

  public abstract ValueType build();
  
  public void setCommon(WxXmlOutMessage m) {
    m.setToUserName(this.toUserName);
    m.setFromUserName(this.fromUserName);
    m.setCreateTime(System.currentTimeMillis() / 1000l);
  }
  
}

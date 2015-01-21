package me.chanjar.weixin.common.session;

public interface WxSessionManager {


  public WxSession getSession(String sessionId);

  public WxSession getSession(String sessionId, boolean create);


}

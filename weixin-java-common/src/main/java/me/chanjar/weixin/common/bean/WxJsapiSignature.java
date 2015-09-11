package me.chanjar.weixin.common.bean;

import java.io.Serializable;

/**
 * jspai signature
 */
public class WxJsapiSignature implements Serializable {
  private String appid;
  
  private String noncestr;

  private long timestamp;

  private String url;

  private String signature;

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getNoncestr() {
    return noncestr;
  }

  public void setNoncestr(String noncestr) {
    this.noncestr = noncestr;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAppid() {
	  return appid;
  }

  public void setAppid(String appid) {
	  this.appid = appid;
  }

}

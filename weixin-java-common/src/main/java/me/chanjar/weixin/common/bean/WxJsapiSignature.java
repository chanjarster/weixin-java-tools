package me.chanjar.weixin.common.bean;

import java.io.Serializable;

/**
 * jspai signature
 */
public class WxJsapiSignature implements Serializable {

  private String noncestr;

  private String jsapiTicket;

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

  public String getJsapiTicket() {
    return jsapiTicket;
  }

  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
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

}

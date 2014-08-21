package chanjarster.weixin.bean;

import chanjarster.weixin.util.WxGsonBuilder;

public class WxError {

  private int errcode;
  
  private String errmsg;

  public int getErrcode() {
    return errcode;
  }

  public void setErrcode(int errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public static WxError fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxError.class);
  }

  @Override
  public String toString() {
    return "{ errcode=" + errcode + ", errmsg=" + errmsg + "}";
  }
  
  
}

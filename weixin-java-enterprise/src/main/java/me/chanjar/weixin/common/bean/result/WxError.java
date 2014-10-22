package me.chanjar.weixin.common.bean.result;

import me.chanjar.weixin.enterprise.util.json.WxCpGsonBuilder;

/**
 * 微信错误码说明
 * http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 * @author Daniel Qian
 *
 */
public class WxError {

  private int errorCode;
  
  private String errorMsg;

  private String json;
  
  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public static WxError fromJson(String json) {
    WxError error = WxCpGsonBuilder.create().fromJson(json, WxError.class);
    error.json = json;
    return error;
  }

  @Override
  public String toString() {
    return "微信错误 errcode=" + errorCode + ", errmsg=" + errorMsg + "\njson:" + json;
  }

}

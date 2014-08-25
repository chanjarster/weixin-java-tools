package chanjarster.weixin.bean.result;

import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * <pre>
 * 群发消息一发送就返回的结果
 * 
 * 真正的群发消息是否发送成功要看
 * http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C
 * 
 * </pre>
 * @author chanjarster
 *
 */
public class WxMassSendResult {

  private String errcode;
  private String errmsg;
  private String msg_id;

  public String getErrcode() {
    return errcode;
  }

  public void setErrcode(String errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public String getMsg_id() {
    return msg_id;
  }

  public void setMsg_id(String msg_id) {
    this.msg_id = msg_id;
  }
  
  public static WxMassSendResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMassSendResult.class);
  }

  @Override
  public String toString() {
    return "WxMassSendResult [errcode=" + errcode + ", errmsg=" + errmsg + ", msg_id=" + msg_id + "]";
  }
  
}

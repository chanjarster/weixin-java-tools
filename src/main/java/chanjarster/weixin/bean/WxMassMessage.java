package chanjarster.weixin.bean;

import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.result.WxMassMaterialUploadResult;
import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 群发消息
 * 
 * @author chanjarster
 */
public class WxMassMessage {
  
  protected List<String> touser = new ArrayList<String>();
  protected String group_id;
  protected String msgtype;
  protected String content;
  protected String media_id;

  public WxMassMessage() {
    super();
  }
  
  /**
   * 利用上传群发所用的素材的结果，构造群发消息
   * @param r
   * @return
   */
  public WxMassMessage(WxMassMaterialUploadResult r) {
    setMedia_id(r.getMedia_id());
    if("video".equals(r.getType())) {
      setMsgtype(WxConsts.MASS_MSG_VIDEO);
    } else {
      setMsgtype(r.getType());
    }
  }
  
  public String getMsgtype() {
    return msgtype;
  }

  /**
   * <pre>
   * 请使用
   * {@link WxConsts#MASS_MSG_IMAGE}
   * {@link WxConsts#MASS_MSG_NEWS}
   * {@link WxConsts#MASS_MSG_TEXT}
   * {@link WxConsts#MASS_MSG_VIDEO}
   * {@link WxConsts#MASS_MSG_VOICE}
   * </pre>
   * @param msgtype
   */
  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMedia_id() {
    return media_id;
  }

  public void setMedia_id(String media_id) {
    this.media_id = media_id;
  }

  public String toJson() {
    return WxGsonBuilder.INSTANCE.create().toJson(this);
  }

  public List<String> getTouser() {
    return touser;
  }

  public void setTouser(List<String> touser) {
    this.touser = touser;
  }

  public String getGroup_id() {
    return group_id;
  }

  public void setGroup_id(String group_id) {
    this.group_id = group_id;
  }

}

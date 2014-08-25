package chanjarster.weixin.bean;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 分组群发的消息
 * 
 * @author chanjarster
 */
public class WxMassGroupMessage {
  
  protected String group_id;
  protected String msgtype;
  protected String content;
  protected String media_id;

  public WxMassGroupMessage() {
    super();
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

  public String getGroup_id() {
    return group_id;
  }

  public void setGroup_id(String group_id) {
    this.group_id = group_id;
  }

}

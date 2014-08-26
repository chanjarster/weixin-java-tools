package chanjarster.weixin.bean;

import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * OpenId列表群发的消息
 * 
 * @author chanjarster
 */
public class WxMassOpenIdsMessage {
  
  private List<String> touser = new ArrayList<String>();
  private String msgtype;
  private String content;
  private String media_id;

  public WxMassOpenIdsMessage() {
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
   * 如果msgtype和media_id不匹配的话，会返回系统繁忙的错误
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

  /**
   * OpenId列表，最多支持10,000个
   * @return
   */
  public List<String> getTouser() {
    return touser;
  }

  /**
   * 添加OpenId，最多支持10,000个
   * @param openId
   */
  public void addUser(String openId) {
    this.touser.add(openId);
  }
}

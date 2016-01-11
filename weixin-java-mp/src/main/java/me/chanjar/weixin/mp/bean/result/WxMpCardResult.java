package me.chanjar.weixin.mp.bean.result;

import me.chanjar.weixin.mp.bean.WxMpCard;

import java.io.Serializable;

/**
 * 卡券查询Code，核销Code接口返回结果
 *
 * @author YuJian
 * @version 15/11/11
 */
public class WxMpCardResult implements Serializable {

  private String errorCode;

  private String errorMsg;

  private String openId;

  private WxMpCard card;

  private String userCardStatus;

  private Boolean canConsume;

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public WxMpCard getCard() {
    return card;
  }

  public void setCard(WxMpCard card) {
    this.card = card;
  }

  @Override
  public String toString() {
    return "WxMpCardResult{" +
        "errorCode='" + errorCode + '\'' +
        ", errorMsg='" + errorMsg + '\'' +
        ", openId='" + openId + '\'' +
        ", card=" + card +
        ", userCardStatus='" + userCardStatus + '\'' +
        ", canConsume=" + canConsume +
        '}';
  }

  public String getUserCardStatus() {
    return userCardStatus;
  }

  public void setUserCardStatus(String userCardStatus) {
    this.userCardStatus = userCardStatus;
  }

  public Boolean getCanConsume() {
    return canConsume;
  }

  public void setCanConsume(Boolean canConsume) {
    this.canConsume = canConsume;
  }

}

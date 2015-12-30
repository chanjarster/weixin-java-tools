package me.chanjar.weixin.mp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
public class WxMpXmlOutTransferCustomerServiceMessage extends WxMpXmlOutMessage {
  @XStreamAlias("TransInfo")
  protected TransInfo transInfo;

  public WxMpXmlOutTransferCustomerServiceMessage() {
    this.msgType = WxConsts.CUSTOM_MSG_TRANSFER_CUSTOMER_SERVICE;
  }

  public TransInfo getTransInfo() {
    return transInfo;
  }

  public void setTransInfo(TransInfo transInfo) {
    this.transInfo = transInfo;
  }

  @XStreamAlias("TransInfo")
  public static class TransInfo {

    @XStreamAlias("KfAccount")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String kfAccount;

    public String getKfAccount() {
      return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
      this.kfAccount = kfAccount;
    }
  }
}

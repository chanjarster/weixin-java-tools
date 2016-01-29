package me.chanjar.weixin.mp.bean.result;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信支付-申请退款返回结果
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 * @author liukaitj
 *
 */
@XStreamAlias("xml")
public class WxMpPayRefundResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @XStreamAlias("return_code")
  private String returnCode;
  
  @XStreamAlias("return_msg")
  private String returnMsg;
  
  @XStreamAlias("result_code")
  private String resultCode;
  
  @XStreamAlias("err_code")
  private String errCode;
  
  @XStreamAlias("err_code_des")
  private String errCodeDes;
  
  @XStreamAlias("appid")
  private String appid;
  
  @XStreamAlias("mch_id")
  private String mchId;
  
  @XStreamAlias("device_info")
  private String deviceInfo;
  
  @XStreamAlias("nonce_str")
  private String nonceStr;
  
  @XStreamAlias("sign")
  private String sign;
  
  @XStreamAlias("transaction_id")
  private String transactionId;
  
  @XStreamAlias("out_trade_no")
  private String outTradeNo;
  
  @XStreamAlias("out_refund_no")
  private String outRefundNo;
  
  @XStreamAlias("refund_id")
  private String refundId;
  
  @XStreamAlias("refund_channel")
  private String refundChannel;
  
  @XStreamAlias("refund_fee")
  private String refundFee;
  
  @XStreamAlias("total_fee")
  private String totalFee;
  
  @XStreamAlias("fee_type")
  private String feeType;
  
  @XStreamAlias("cash_fee")
  private String cashFee;
  
  @XStreamAlias("cash_refund_fee")
  private String cashRefundfee;
  
  @XStreamAlias("coupon_refund_fee")
  private String couponRefundFee;
  
  @XStreamAlias("coupon_refund_count")
  private String couponRefundCount;
  
  @XStreamAlias("coupon_refund_id")
  private String couponRefundId;

  public String getReturnCode() {
    return returnCode;
  }
  
  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }
  
  public String getReturnMsg() {
    return returnMsg;
  }
  
  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }
  
  public String getResultCode() {
    return resultCode;
  }
  
  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }
  
  public String getErrCode() {
    return errCode;
  }
  
  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }
  
  public String getErrCodeDes() {
    return errCodeDes;
  }
  
  public void setErrCodeDes(String errCodeDes) {
    this.errCodeDes = errCodeDes;
  }
  
  public String getAppid() {
    return appid;
  }
  
  public void setAppid(String appid) {
    this.appid = appid;
  }
  
  public String getMchId() {
    return mchId;
  }
  
  public void setMchId(String mchId) {
    this.mchId = mchId;
  }
  
  public String getDeviceInfo() {
    return deviceInfo;
  }
  
  public void setDeviceInfo(String deviceInfo) {
    this.deviceInfo = deviceInfo;
  }
  
  public String getNonceStr() {
    return nonceStr;
  }
  
  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }
  
  public String getSign() {
    return sign;
  }
  
  public void setSign(String sign) {
    this.sign = sign;
  }
  
  public String getTransactionId() {
    return transactionId;
  }
  
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
  
  public String getOutTradeNo() {
    return outTradeNo;
  }
  
  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }
  
  public String getOutRefundNo() {
    return outRefundNo;
  }
  
  public void setOutRefundNo(String outRefundNo) {
    this.outRefundNo = outRefundNo;
  }
  
  public String getRefundId() {
    return refundId;
  }
  
  public void setRefundId(String refundId) {
    this.refundId = refundId;
  }
  
  public String getRefundChannel() {
    return refundChannel;
  }
  
  public void setRefundChannel(String refundChannel) {
    this.refundChannel = refundChannel;
  }
  
  public String getRefundFee() {
    return refundFee;
  }
  
  public void setRefundFee(String refundFee) {
    this.refundFee = refundFee;
  }
  
  public String getTotalFee() {
    return totalFee;
  }
  
  public void setTotalFee(String totalFee) {
    this.totalFee = totalFee;
  }
  
  public String getFeeType() {
    return feeType;
  }
  
  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }
  
  public String getCashFee() {
    return cashFee;
  }
  
  public void setCashFee(String cashFee) {
    this.cashFee = cashFee;
  }
  
  public String getCashRefundfee() {
    return cashRefundfee;
  }
  
  public void setCashRefundfee(String cashRefundfee) {
    this.cashRefundfee = cashRefundfee;
  }
  
  public String getCouponRefundFee() {
    return couponRefundFee;
  }
  
  public void setCouponRefundFee(String couponRefundFee) {
    this.couponRefundFee = couponRefundFee;
  }
  
  public String getCouponRefundCount() {
    return couponRefundCount;
  }
  
  public void setCouponRefundCount(String couponRefundCount) {
    this.couponRefundCount = couponRefundCount;
  }
  
  public String getCouponRefundId() {
    return couponRefundId;
  }
  
  public void setCouponRefundId(String couponRefundId) {
    this.couponRefundId = couponRefundId;
  }
  
  @Override
  public String toString() {
    return "[" +
      "return_code:" + returnCode + ";" +
      "return_msg" + returnMsg + ";";
  }

}

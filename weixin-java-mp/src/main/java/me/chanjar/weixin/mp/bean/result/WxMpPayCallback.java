package me.chanjar.weixin.mp.bean.result;

import java.io.Serializable;

/**
 * pre> 订单支付状态回调
 * 
 * 支付结果通知(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7)
 *
 * /pre>
 *
 * @author ukid
 */
public class WxMpPayCallback implements Serializable {
    private String return_code;
    private String return_msg;

    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String openid;
    private String is_subscribe;
    private String trade_type;
    private String bank_type;
    private String total_fee;
    private String fee_type;
    private String cash_fee;
    private String cash_fee_type;
    private String coupon_fee;
    private String coupon_count;
    private String coupon_batch_id_$n;
    private String coupon_id_$n;
    private String coupon_fee_$n;
    private String transaction_id;
    private String out_trade_no;
    private String attach;
    private String time_end;

    public String getReturn_code() {
        return return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public String getOpenid() {
        return openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public String getCoupon_count() {
        return coupon_count;
    }

    public String getCoupon_batch_id_$n() {
        return coupon_batch_id_$n;
    }

    public String getCoupon_id_$n() {
        return coupon_id_$n;
    }

    public String getCoupon_fee_$n() {
        return coupon_fee_$n;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getAttach() {
        return attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public void setCoupon_count(String coupon_count) {
        this.coupon_count = coupon_count;
    }

    public void setCoupon_batch_id_$n(String coupon_batch_id_$n) {
        this.coupon_batch_id_$n = coupon_batch_id_$n;
    }

    public void setCoupon_id_$n(String coupon_id_$n) {
        this.coupon_id_$n = coupon_id_$n;
    }

    public void setCoupon_fee_$n(String coupon_fee_$n) {
        this.coupon_fee_$n = coupon_fee_$n;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    @Override
    public String toString() {
        return "WxMpPayCallback [return_code=" + return_code + ", return_msg="
                + return_msg + ", appid=" + appid + ", mch_id=" + mch_id
                + ", device_info=" + device_info + ", nonce_str=" + nonce_str
                + ", sign=" + sign + ", result_code=" + result_code
                + ", err_code=" + err_code + ", err_code_des=" + err_code_des
                + ", openid=" + openid + ", is_subscribe=" + is_subscribe
                + ", trade_type=" + trade_type + ", bank_type=" + bank_type
                + ", total_fee=" + total_fee + ", fee_type=" + fee_type
                + ", cash_fee=" + cash_fee + ", cash_fee_type=" + cash_fee_type
                + ", coupon_fee=" + coupon_fee + ", coupon_count="
                + coupon_count + ", coupon_batch_id_$n=" + coupon_batch_id_$n
                + ", coupon_id_$n=" + coupon_id_$n + ", coupon_fee_$n="
                + coupon_fee_$n + ", transaction_id=" + transaction_id
                + ", out_trade_no=" + out_trade_no + ", attach=" + attach
                + ", time_end=" + time_end + "]";
    }

}

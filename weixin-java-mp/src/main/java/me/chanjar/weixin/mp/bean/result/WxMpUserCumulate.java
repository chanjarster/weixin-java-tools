package me.chanjar.weixin.mp.bean.result;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 累计用户数据接口的返回JSON数据包
 * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
 * </pre>
 */
public class WxMpUserCumulate implements Serializable {

  private Date refDate;

  private Integer cumulateUser;

  public Date getRefDate() {
    return refDate;
  }

  public void setRefDate(Date refDate) {
    this.refDate = refDate;
  }

  public Integer getCumulateUser() {
    return cumulateUser;
  }

  public void setCumulateUser(Integer cumulateUser) {
    this.cumulateUser = cumulateUser;
  }

  @Override
  public String toString() {
    return "WxMpUserCumulate{" +
        "refDate=" + refDate +
        ", cumulateUser=" + cumulateUser +
        '}';
  }
}

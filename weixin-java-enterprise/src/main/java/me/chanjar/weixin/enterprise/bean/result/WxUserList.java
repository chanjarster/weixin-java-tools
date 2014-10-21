package me.chanjar.weixin.enterprise.bean.result;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.enterprise.util.json.WxCpGsonBuilder;

/**
 * 关注者列表
 * @author Daniel Qian
 *
 */
public class WxUserList {

  protected int total = -1;
  protected int count = -1;
  protected List<String> openIds = new ArrayList<String>();
  protected String nextOpenId;
  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public List<String> getOpenIds() {
    return openIds;
  }
  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }
  public String getNextOpenId() {
    return nextOpenId;
  }
  public void setNextOpenId(String nextOpenId) {
    this.nextOpenId = nextOpenId;
  }
  
  public static WxUserList fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxUserList.class);
  }
}

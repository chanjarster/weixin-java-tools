package chanjarster.weixin.bean.result;

import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 换取二维码的Ticket
 * 
 * @author chanjarster
 */
public class WxQrCodeTicket {
  
  protected String ticket;
  protected int expire_seconds = -1;
  protected String url;

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  /**
   * 如果返回-1说明是永久
   */
  public int getExpire_seconds() {
    return expire_seconds;
  }

  public void setExpire_seconds(int expire_seconds) {
    this.expire_seconds = expire_seconds;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public static WxQrCodeTicket fromJson(String json) {
    return WxGsonBuilder.INSTANCE.create().fromJson(json, WxQrCodeTicket.class);
  }
}

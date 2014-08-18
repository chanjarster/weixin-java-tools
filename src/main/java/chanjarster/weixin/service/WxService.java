package chanjarster.weixin.service;

import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.out.WxCustomMessage;
import chanjarster.weixin.out.WxMenu;

/**
 * 微信相关的常量
 */
public interface WxService {
  
  public void refreshAccessToken() throws WxErrorException;
  
  public String sendCustomMessage(WxCustomMessage message) throws WxErrorException;
  
  public String createMenu(WxMenu menu) throws WxErrorException;
  
  public String deleteMenu() throws WxErrorException;
  
  public WxMenu getMenu() throws WxErrorException;

  public void setWxConfigProvider(WxConfigProvider wxConfigProvider);
}

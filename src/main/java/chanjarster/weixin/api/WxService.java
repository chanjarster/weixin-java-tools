package chanjarster.weixin.api;

import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.exception.WxErrorException;

/**
 * 微信API的Service
 */
public interface WxService {
  
  /**
   * 刷新access_token，这个方法是线程安全的
   * 且在高并发情况下只会刷新一次，而不是刷新多次
   * 在非必要情况下不要主动调用此方法
   * 本service的所有方法都会在access_token过期的情况下调用此方法
   * 
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * @throws WxErrorException
   */
  public void refreshAccessToken() throws WxErrorException;
  
  /**
   * 发送客服消息
   * @param message
   * @return
   * @throws WxErrorException
   */
  public String sendCustomMessage(WxCustomMessage message) throws WxErrorException;
  
  /**
   * 创建菜单
   * @param menu
   * @return
   * @throws WxErrorException
   */
  public String createMenu(WxMenu menu) throws WxErrorException;
  
  /**
   * 删除菜单
   * @return
   * @throws WxErrorException
   */
  public String deleteMenu() throws WxErrorException;
  
  /**
   * 获得菜单
   * @return
   * @throws WxErrorException
   */
  public WxMenu getMenu() throws WxErrorException;

  public void setWxConfigProvider(WxConfigProvider wxConfigProvider);
}

package chanjarster.weixin.api;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.api.ApiTestModule.WxXmlConfigStorage;
import chanjarster.weixin.bean.result.WxUser;
import chanjarster.weixin.bean.result.WxUserList;
import chanjarster.weixin.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 测试用户相关的接口
 * @author chanjarster
 *
 */
@Test(groups = "userAPI", dependsOnGroups = { "baseAPI", "groupAPI" })
@Guice(modules = ApiTestModule.class)
public class WxUserAPITest {

  @Inject
  protected WxServiceImpl wxService;

  public void testUserUpdateRemark() throws WxErrorException {
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigStorage;
    wxService.userUpdateRemark(configProvider.getOpenId(), "测试备注名");
  }

  public void testUserInfo() throws WxErrorException  {
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigStorage;
    WxUser user = wxService.userInfo(configProvider.getOpenId(), null);
    Assert.assertNotNull(user);
  }
  
  public void testUserList() throws WxErrorException  {
      WxUserList wxUserList = wxService.userList(null);
      Assert.assertNotNull(wxUserList);
      Assert.assertFalse(wxUserList.getCount() == -1);
      Assert.assertFalse(wxUserList.getTotal() == -1);
      Assert.assertFalse(wxUserList.getOpenids().size() == -1);
  }
    
  public void testGroupQueryUserGroup() throws WxErrorException {
    WxXmlConfigStorage configStorage = (WxXmlConfigStorage) wxService.wxConfigStorage;
    long groupid = wxService.userGetGroup(configStorage.getOpenId());
    Assert.assertTrue(groupid != -1l);
  }
  
  public void getGroupMoveUser() throws WxErrorException {
    WxXmlConfigStorage configStorage = (WxXmlConfigStorage) wxService.wxConfigStorage;
    wxService.userUpdateGroup(configStorage.getOpenId(), wxService.groupGet().get(3).getId());
  }
  
}

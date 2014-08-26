package chanjarster.weixin.api;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.api.ApiTestModule.WxXmlConfigStorage;
import chanjarster.weixin.bean.result.WxUser;
import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.util.json.WxGsonBuilder;

import com.google.inject.Inject;

/**
 * 测试用户相关的接口
 * @author chanjarster
 *
 */
@Test(groups = "userAPI", dependsOnGroups = { "baseAPI" })
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
    System.out.println(WxGsonBuilder.INSTANCE.create().toJson(user));
  }
  
}

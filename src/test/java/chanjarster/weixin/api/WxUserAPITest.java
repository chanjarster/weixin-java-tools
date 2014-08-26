package chanjarster.weixin.api;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.api.ApiTestModule.WxXmlConfigStorage;
import chanjarster.weixin.exception.WxErrorException;

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

  @Test
  public void testUserUpdateRemark() throws WxErrorException {
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigStorage;
    wxService.userUpdateRemark(configProvider.getOpenId(), "测试备注名");
  }

}

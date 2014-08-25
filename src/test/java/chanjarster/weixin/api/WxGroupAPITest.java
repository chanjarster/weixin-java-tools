package chanjarster.weixin.api;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.bean.WxGroup;
import chanjarster.weixin.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 测试分组接口
 * 
 * @author chanjarster
 */
@Test(groups = "groupAPI", dependsOnGroups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxGroupAPITest {

  @Inject
  protected WxServiceImpl wxService;

  public void testCreateMenu() throws WxErrorException {
    WxGroup res = wxService.groupCreate("测试分组1");
    Assert.assertEquals(res.getName(), "测试分组1");
  }

}

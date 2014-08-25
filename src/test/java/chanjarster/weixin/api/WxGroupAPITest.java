package chanjarster.weixin.api;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.api.ApiTestModule.WxXmlConfigStorage;
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

  protected WxGroup group;
  
  public void testGroupCreate() throws WxErrorException {
    WxGroup res = wxService.groupCreate("测试分组1");
    Assert.assertEquals(res.getName(), "测试分组1");
  }

  @Test(dependsOnMethods="testGroupCreate")
  public void testGroupGet() throws WxErrorException {
    List<WxGroup> groupList = wxService.groupGet();
    Assert.assertNotNull(groupList);
    Assert.assertTrue(groupList.size() > 0);
    for (WxGroup g : groupList) {
      group = g;
      Assert.assertNotNull(g.getName());
    }
  }
  
  @Test(dependsOnMethods="testGroupCreate")
  public void testGroupQueryUserGroup() throws WxErrorException {
    WxXmlConfigStorage configStorage = (WxXmlConfigStorage) wxService.wxConfigStorage;
    long groupid = wxService.groupQueryUserGroup(configStorage.getOpenId());
    Assert.assertTrue(groupid != -1l);
  }
  
  @Test(dependsOnMethods={"testGroupGet", "testGroupCreate"})
  public void getGroupUpdate() throws WxErrorException {
    group.setName("分组改名");
    wxService.groupUpdate(group);
  }

  @Test(dependsOnMethods={"testGroupGet", "testGroupCreate"})
  public void getGroupMoveUser() throws WxErrorException {
    WxXmlConfigStorage configStorage = (WxXmlConfigStorage) wxService.wxConfigStorage;
    wxService.groupMoveUser(configStorage.getOpenId(), group.getId());
  }
}

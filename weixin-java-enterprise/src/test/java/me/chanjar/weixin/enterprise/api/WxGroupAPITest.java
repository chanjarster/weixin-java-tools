package me.chanjar.weixin.enterprise.api;

import java.util.List;

import me.chanjar.weixin.enterprise.bean.WxDepartment;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import me.chanjar.weixin.enterprise.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 测试分组接口
 * 
 * @author Daniel Qian
 */
@Test(groups = "groupAPI", dependsOnGroups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxGroupAPITest {

  @Inject
  protected WxCpServiceImpl wxService;

  protected WxDepartment group;
  
  public void testGroupCreate() throws WxErrorException {
    WxDepartment res = wxService.departmentCreate("测试分组1");
    Assert.assertEquals(res.getName(), "测试分组1");
  }

  @Test(dependsOnMethods="testGroupCreate")
  public void testGroupGet() throws WxErrorException {
    List<WxDepartment> groupList = wxService.departmentGet();
    Assert.assertNotNull(groupList);
    Assert.assertTrue(groupList.size() > 0);
    for (WxDepartment g : groupList) {
      group = g;
      Assert.assertNotNull(g.getName());
    }
  }
  
  @Test(dependsOnMethods={"testGroupGet", "testGroupCreate"})
  public void getGroupUpdate() throws WxErrorException {
    group.setName("分组改名");
    wxService.departmentUpdate(group);
  }

}

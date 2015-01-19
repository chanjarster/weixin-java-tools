package me.chanjar.weixin.cp.api;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.List;

/**
 * 测试部门接口
 *
 * @author Daniel Qian
 */
@Test(groups = "departAPI", dependsOnGroups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxCpDepartAPITest {

  @Inject
  protected WxCpServiceImpl wxCpService;

  protected WxCpDepart depart;

  public void testDepartCreate() throws WxErrorException {
    WxCpDepart depart = new WxCpDepart();
    depart.setName("子部门" + System.currentTimeMillis());
    depart.setParentId(1);
    depart.setOrder(1);
    Integer departId = wxCpService.departCreate(depart);
  }

  @Test(dependsOnMethods = "testDepartCreate")
  public void testDepartGet() throws WxErrorException {
    System.out.println("=================获取部门");
    List<WxCpDepart> departList = wxCpService.departGet();
    Assert.assertNotNull(departList);
    Assert.assertTrue(departList.size() > 0);
    for (WxCpDepart g : departList) {
      depart = g;
      System.out.println(depart.getId() + ":" + depart.getName());
      Assert.assertNotNull(g.getName());
    }
  }

  @Test(dependsOnMethods = { "testDepartGet", "testDepartCreate" })
  public void testDepartUpdate() throws WxErrorException {
    System.out.println("=================更新部门");
    depart.setName("子部门改名" + System.currentTimeMillis());
    wxCpService.departUpdate(depart);
  }

  @Test(dependsOnMethods = "testDepartUpdate")
  public void testDepartDelete() throws WxErrorException {
    System.out.println("=================删除部门");
    System.out.println(depart.getId() + ":" + depart.getName());
    wxCpService.departDelete(depart.getId());
  }

}

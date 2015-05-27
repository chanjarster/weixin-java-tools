package me.chanjar.weixin.cp.api;

import com.google.inject.Inject;
import me.chanjar.weixin.cp.bean.WxCpTag;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test(groups = "departAPI", dependsOnGroups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxCpTagAPITest {

  @Inject
  protected WxCpServiceImpl wxService;

  @Inject
  protected WxCpConfigStorage configStorage;

  protected String tagId;

  public void testTagCreate() throws Exception {
    tagId = wxService.tagCreate("测试标签4");
    System.out.println(tagId);
  }

  @Test(dependsOnMethods = "testTagCreate")
  public void testTagUpdate() throws Exception {
    wxService.tagUpdate(tagId, "测试标签-改名");
  }

  @Test(dependsOnMethods = "testTagUpdate")
  public void testTagGet() throws Exception {
    List<WxCpTag> tags = wxService.tagGet();
    Assert.assertNotEquals(tags.size(), 0);
  }

  @Test(dependsOnMethods = "testTagGet")
  public void testTagAddUsers() throws Exception {
    List<String> userIds = new ArrayList<String>();
    userIds.add(((ApiTestModule.WxXmlCpInMemoryConfigStorage)configStorage).getUserId());
    wxService.tagAddUsers(tagId, userIds, null);
  }

  @Test(dependsOnMethods = "testTagAddUsers")
  public void testTagGetUsers() throws Exception {
    List<WxCpUser> users = wxService.tagGetUsers(tagId);
    Assert.assertNotEquals(users.size(), 0);
  }

  @Test(dependsOnMethods = "testTagGetUsers")
  public void testTagRemoveUsers() throws Exception {
    List<String> userIds = new ArrayList<String>();
    userIds.add(((ApiTestModule.WxXmlCpInMemoryConfigStorage)configStorage).getUserId());
    wxService.tagRemoveUsers(tagId, userIds);
  }

  @Test(dependsOnMethods = "testTagRemoveUsers")
  public void testTagDelete() throws Exception {
    wxService.tagDelete(tagId);
  }

}

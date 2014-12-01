package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import me.chanjar.weixin.common.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 基础API测试
 * @author chanjarster
 *
 */
@Test(groups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxBaseAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testRefreshAccessToken() throws WxErrorException {
    WxMpConfigStorage configStorage = wxService.wxMpConfigStorage;
    String before = configStorage.getAccessToken();
    wxService.accessTokenRefresh();

    String after = configStorage.getAccessToken();

    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }

}

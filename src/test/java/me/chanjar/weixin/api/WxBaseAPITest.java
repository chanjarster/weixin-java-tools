package me.chanjar.weixin.api;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import me.chanjar.weixin.exception.WxErrorException;

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
  protected WxServiceImpl wxService;

  public void testRefreshAccessToken() throws WxErrorException {
    WxConfigStorage configStorage = wxService.wxConfigStorage;
    String before = configStorage.getAccessToken();
    wxService.accessTokenRefresh();

    String after = configStorage.getAccessToken();

    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }

  public void testCheckSignature() throws WxErrorException {
    String timestamp = "1413729506";
    String nonce = "1753282854";
    String signature = "af210121811dce2d6f306612cb133cba490e818b";
    Assert.assertTrue(wxService.checkSignature(timestamp, nonce, signature));
  }

}

package chanjarster.weixin.api;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.exception.WxErrorException;

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
    String timestamp = "23234235423246";
    String nonce = "y7didfkcmvnbd90sdofjkiefhsd";
    String signature = "77b6651628dfb9a64bfb0d3432ee053ac566a459";
    Assert.assertTrue(wxService.checkSignature(timestamp, nonce, signature));
  }

}

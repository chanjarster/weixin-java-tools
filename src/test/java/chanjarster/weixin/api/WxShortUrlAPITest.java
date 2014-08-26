package chanjarster.weixin.api;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 测试用户相关的接口
 * 
 * @author chanjarster
 */
@Test(groups = "shortURLAPI", dependsOnGroups = { "baseAPI" })
@Guice(modules = ApiTestModule.class)
public class WxShortUrlAPITest {

  @Inject
  protected WxServiceImpl wxService;

  public void testShortUrl() throws WxErrorException {
    String shortUrl = wxService.shortUrl("www.baidu.com");
    Assert.assertNotNull(shortUrl);
  }

}

package me.chanjar.weixin.mp.api;

import com.google.inject.Inject;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassGroupMessage;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 *
 * @author chanjarster
 *
 */
@Test(groups = "miscAPI", dependsOnGroups = { "baseAPI"})
@Guice(modules = ApiTestModule.class)
public class WxMpMiscAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  @Test
  public void getCallbackIP() throws WxErrorException {
    String[] ipArray = wxService.getCallbackIP();
    System.out.println(Arrays.toString(ipArray));
    Assert.assertNotNull(ipArray);
    Assert.assertNotEquals(ipArray.length, 0);
  }

}

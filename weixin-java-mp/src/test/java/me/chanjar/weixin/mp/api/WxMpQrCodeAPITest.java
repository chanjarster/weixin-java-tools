package me.chanjar.weixin.mp.api;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.File;

/**
 * 测试用户相关的接口
 * 
 * @author chanjarster
 */
@Test(groups = "qrCodeAPI", dependsOnGroups = { "baseAPI" })
@Guice(modules = ApiTestModule.class)
public class WxMpQrCodeAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testQrCodeCreateTmpTicket() throws WxErrorException {
    WxMpQrCodeTicket ticket = wxService.qrCodeCreateTmpTicket(1, null);
    Assert.assertNotNull(ticket.getUrl());
    Assert.assertNotNull(ticket.getTicket());
    Assert.assertTrue(ticket.getExpire_seconds() != -1);
  }

  public void testQrCodeCreateLastTicket() throws WxErrorException {
    WxMpQrCodeTicket ticket = wxService.qrCodeCreateLastTicket(1);
    Assert.assertNotNull(ticket.getUrl());
    Assert.assertNotNull(ticket.getTicket());
    Assert.assertTrue(ticket.getExpire_seconds() == -1);
  }

  public void testQrCodePicture() throws WxErrorException {
    WxMpQrCodeTicket ticket = wxService.qrCodeCreateLastTicket(1);
    File file = wxService.qrCodePicture(ticket);
    Assert.assertNotNull(file);
  }

}

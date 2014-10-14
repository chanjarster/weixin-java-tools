package me.chanjar.weixin.bean;

import me.chanjar.weixin.bean.WxAccessToken;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxAccessTokenTest {

  public void testFromJson() {

    String json = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
    WxAccessToken wxError = WxAccessToken.fromJson(json);
    Assert.assertEquals(wxError.getAccess_token(), "ACCESS_TOKEN");
    Assert.assertTrue(wxError.getExpires_in() == 7200);

  }
  
}

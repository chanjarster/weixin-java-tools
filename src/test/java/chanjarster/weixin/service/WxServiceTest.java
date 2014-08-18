package chanjarster.weixin.service;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.out.WxCustomMessage;
import chanjarster.weixin.util.XmlTransformer;

public class WxServiceTest {

  @Test(dataProvider = "configs")
  public void testRefreshAccessToken(WxConfigProvider config) throws WxErrorException {
    String before = config.getAccessToken();
    
    WxService wxService = new WxServiceImpl();
    wxService.setWxConfigProvider(config);
    wxService.refreshAccessToken();
    
    String after = config.getAccessToken();
    
    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }
  
  @Test(dataProvider = "configs")
  public void sendCustomMessage(SimpleWxConfigProvider config) throws WxErrorException {
    WxService wxService = new WxServiceImpl();
    wxService.setWxConfigProvider(config);
    
    WxCustomMessage message = new WxCustomMessage();
    message.setMsgtype(WxMsgType.TEXT);
    message.setTouser(config.getOpenId());
    message.setContent("欢迎使用教务系统微信公众号\n下面\n<a href=\"http://192.168.1.249:9180/eams-rc/login.action\">Hello World</a>");

    wxService.sendCustomMessage(message);
  }
  /**
   * 返回新的access_token
   * @return
   * @throws JAXBException 
   */
  @DataProvider(name = "configs")
  public Object[][] getConfig() throws JAXBException {
    /**
     * 将 src/test/resources/test-config.sample.xml 改成 test-config.xml 并设置appId, secret, 一个过期的accessToken
     */
    // 没有access_token
    InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
    SimpleWxConfigProvider config1 = XmlTransformer.fromXml(SimpleWxConfigProvider.class, is1);
    return new Object[][] {
        new Object[] {
            config1
        }
    };
  }
  
  @XmlRootElement(name = "xml")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class SimpleWxConfigProvider implements WxConfigProvider {
    private String appId;
    private String secret;
    private String accessToken = "";
    private Integer expiresIn;
    private String token;
    private String openId;
    public void updateAccessToken(String accessToken, Integer expiresIn) {
      this.accessToken = accessToken;
      this.expiresIn = expiresIn;
    }
    public String getAccessToken() {
      return accessToken;
    }
    public String getAppId() {
      return appId;
    }
    public String getSecret() {
      return secret;
    }
    public String getToken() {
      return token;
    }
    public void setAppId(String appId) {
      this.appId = appId;
    }
    public void setSecret(String secret) {
      this.secret = secret;
    }
    public void setToken(String token) {
      this.token = token;
    }
    public Integer getExpiresIn() {
      return expiresIn;
    }
    public void setExpiresIn(Integer expiresIn) {
      this.expiresIn = expiresIn;
    }
    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }
    public String getOpenId() {
      return openId;
    }
    public void setOpenId(String openId) {
      this.openId = openId;
    }
    @Override
    public String toString() {
      return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken
          + ", expiresIn=" + expiresIn + ", token=" + token + ", openId=" + openId + "]";
    }
     
  }
  
  
}

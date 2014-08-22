package chanjarster.weixin.api;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.bean.WxMenu.WxMenuButton;
import chanjarster.weixin.exception.WxErrorException;
import chanjarster.weixin.util.XmlTransformer;

public class WxServiceTest {

  private WxServiceImpl wxService;
  
  @BeforeTest
  public void prepare() throws JAXBException {
    InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
    WxXmlConfigStorage config1 = XmlTransformer.fromXml(WxXmlConfigStorage.class, is1);
    this.wxService = new WxServiceImpl();
    this.wxService.setWxConfigProvider(config1);
  }
  
  @Test
  public void testRefreshAccessToken() throws WxErrorException {
    WxConfigStorage configProvider = wxService.wxConfigProvider;
    String before = configProvider.getAccessToken();
    wxService.refreshAccessToken();
    
    String after = configProvider.getAccessToken();
    
    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }
  
  @Test(dependsOnMethods = "testRefreshAccessToken")
  public void sendCustomMessage() throws WxErrorException {
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigProvider;
    WxCustomMessage message = new WxCustomMessage();
    message.setMsgtype(WxConsts.MSG_TEXT);
    message.setTouser(configProvider.getOpenId());
    message.setContent("欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");

    wxService.sendCustomMessage(message);
  }
  
  @Test(dataProvider = "menu", dependsOnMethods = "testRefreshAccessToken")
  public void testCreateMenu(WxMenu wxMenu) throws WxErrorException {
    wxService.createMenu(wxMenu);
  }
  
  @Test(dependsOnMethods = { "testRefreshAccessToken" , "testCreateMenu"})
  public void testGetMenu() throws WxErrorException {
    Assert.assertNotNull(wxService.getMenu());
  }
  
  @Test(dependsOnMethods = { "testRefreshAccessToken", "testGetMenu" })
  public void testDeleteMenu() throws WxErrorException {
    wxService.deleteMenu();
  }
  
  @DataProvider(name="menu")
  public Object[][] getMenu() throws JAXBException {
    WxMenu menu = new WxMenu();
    WxMenuButton button1 = new WxMenuButton();
    button1.setType("click");
    button1.setName("今日歌曲");
    button1.setKey("V1001_TODAY_MUSIC");
    
    WxMenuButton button2 = new WxMenuButton();
    button2.setType("click");
    button2.setName("歌手简介");
    button2.setKey("V1001_TODAY_SINGER");
    
    WxMenuButton button3 = new WxMenuButton();
    button3.setName("菜单");
    
    menu.getButton().add(button1);
    menu.getButton().add(button2);
    menu.getButton().add(button3);
    
    WxMenuButton button31 = new WxMenuButton();
    button31.setType("view");
    button31.setName("搜索");
    button31.setUrl("http://www.soso.com/");
    
    WxMenuButton button32 = new WxMenuButton();
    button32.setType("view");
    button32.setName("视频");
    button32.setUrl("http://v.qq.com/");
    
    WxMenuButton button33 = new WxMenuButton();
    button33.setType("click");
    button33.setName("赞一下我们");
    button33.setKey("V1001_GOOD");
    
    button3.getSub_button().add(button31);
    button3.getSub_button().add(button32);
    button3.getSub_button().add(button33);
    
    return new Object[][] {
        new Object[] {
            menu
        }
    };
  
  }
  
  
  @XmlRootElement(name = "xml")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class WxXmlConfigStorage extends WxInMemoryConfigStorage {
    
    protected String openId;
    
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

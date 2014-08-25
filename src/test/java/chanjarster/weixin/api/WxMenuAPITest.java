package chanjarster.weixin.api;

import javax.xml.bind.JAXBException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.bean.WxMenu.WxMenuButton;
import chanjarster.weixin.exception.WxErrorException;

/**
 * 测试菜单
 * @author chanjarster
 *
 */
@Test(dependsOnGroups="baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxMenuAPITest {

  @Inject
  protected WxServiceImpl wxService;
  
  @Test(dataProvider = "menu", enabled = true)
  public void testCreateMenu(WxMenu wxMenu) throws WxErrorException {
    wxService.menuCreate(wxMenu);
  }
  
  @Test(dependsOnMethods = { "testCreateMenu"}, enabled = true)
  public void testGetMenu() throws WxErrorException {
    Assert.assertNotNull(wxService.menuGet());
  }
  
  @Test(dependsOnMethods = { "testGetMenu"}, enabled = true)
  public void testDeleteMenu() throws WxErrorException {
    wxService.menuDelete();
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
  
  
}

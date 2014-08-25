package chanjarster.weixin.api;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import chanjarster.weixin.api.WxBaseAPITest.WxXmlConfigStorage;
import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.exception.WxErrorException;

@Test(dependsOnGroups="baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxCustomMessageAPITest {

  @Inject
  protected WxServiceImpl wxService;
  
  @Test(enabled = true)
  public void testSendCustomMessage() throws WxErrorException {
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigStorage;
    WxCustomMessage message = new WxCustomMessage();
    message.setMsgtype(WxConsts.CUSTOM_MSG_TEXT);
    message.setTouser(configProvider.getOpenId());
    message.setContent("欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");

    wxService.sendCustomMessage(message);
  }
  
}

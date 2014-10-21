package me.chanjar.weixin.enterprise.api;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import me.chanjar.weixin.enterprise.bean.WxCustomMessage;
import me.chanjar.weixin.enterprise.exception.WxErrorException;

import com.google.inject.Inject;

/***
 * 测试发送客服消息
 * @author Daniel Qian
 *
 */
@Test(groups="customMessageAPI", dependsOnGroups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxCustomMessageAPITest {

  @Inject
  protected WxCpServiceImpl wxService;

  public void testSendCustomMessage() throws WxErrorException {
    ApiTestModule.WxXmlCpConfigStorage configStorage = (ApiTestModule.WxXmlCpConfigStorage) wxService.wxCpConfigStorage;
    WxCustomMessage message = new WxCustomMessage();
    message.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
    message.setToUser(configStorage.getOpenId());
    message.setContent("欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");

    wxService.messageSend(message);
  }

}

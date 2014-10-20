package me.chanjar.weixin.api;

import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import me.chanjar.weixin.api.ApiTestModule.WxXmlConfigStorage;
import me.chanjar.weixin.bean.WxMassGroupMessage;
import me.chanjar.weixin.bean.WxMassNews;
import me.chanjar.weixin.bean.WxMassNews.WxMassNewsArticle;
import me.chanjar.weixin.bean.WxMassOpenIdsMessage;
import me.chanjar.weixin.bean.WxMassVideo;
import me.chanjar.weixin.bean.result.WxMassSendResult;
import me.chanjar.weixin.bean.result.WxMassUploadResult;
import me.chanjar.weixin.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 测试群发消息
 * @author chanjarster
 *
 */
@Test(groups = "massAPI", dependsOnGroups = { "baseAPI", "mediaAPI", "groupAPI"})
@Guice(modules = ApiTestModule.class)
public class WxMassMessageAPITest {

  @Inject
  protected WxServiceImpl wxService;

  @Test
  public void testTextMassOpenIdsMessageSend() throws WxErrorException {
    // 发送群发消息
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigStorage;
    WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
    massMessage.setMsgType(WxConsts.MASS_MSG_TEXT);
    massMessage.setContent("测试群发消息\n欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");
    massMessage.getToUsers().add(configProvider.getOpenId());

    WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
    Assert.assertNotNull(massResult);
    Assert.assertNotNull(massResult.getMsgId());
  }

  @Test(dataProvider="massMessages")
  public void testMediaMassOpenIdsMessageSend(String massMsgType, String mediaId) throws WxErrorException, IOException {
    // 发送群发消息
    WxXmlConfigStorage configProvider = (WxXmlConfigStorage) wxService.wxConfigStorage;
    WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
    massMessage.setMsgType(massMsgType);
    massMessage.setMediaId(mediaId);
    massMessage.getToUsers().add(configProvider.getOpenId());

    WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
    Assert.assertNotNull(massResult);
    Assert.assertNotNull(massResult.getMsgId());
  }

  @Test
  public void testTextMassGroupMessageSend() throws WxErrorException {
    WxMassGroupMessage massMessage = new WxMassGroupMessage();
    massMessage.setMsgtype(WxConsts.MASS_MSG_TEXT);
    massMessage.setContent("测试群发消息\n欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");
    massMessage.setGroupId(wxService.groupGet().get(0).getId());
    
    WxMassSendResult massResult = wxService.massGroupMessageSend(massMessage);
    Assert.assertNotNull(massResult);
    Assert.assertNotNull(massResult.getMsgId());
  }
  
  @Test(dataProvider="massMessages")
  public void testMediaMassGroupMessageSend(String massMsgType, String mediaId) throws WxErrorException, IOException {
    WxMassGroupMessage massMessage = new WxMassGroupMessage();
    massMessage.setMsgtype(massMsgType);
    massMessage.setMediaId(mediaId);
    massMessage.setGroupId(wxService.groupGet().get(0).getId());

    WxMassSendResult massResult = wxService.massGroupMessageSend(massMessage);
    Assert.assertNotNull(massResult);
    Assert.assertNotNull(massResult.getMsgId());
  }
  
  @DataProvider
  public Object[][] massMessages() throws WxErrorException, IOException {
    Object[][] messages = new Object[4][];
    /*
     * 视频素材
     */
    {
      // 上传视频到媒体库
      InputStream inputStream = ClassLoader.getSystemResourceAsStream("mm.mp4");
      WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, inputStream);
      Assert.assertNotNull(uploadMediaRes);
      Assert.assertNotNull(uploadMediaRes.getMediaId());
      
      // 把视频变成可被群发的媒体
      WxMassVideo video = new WxMassVideo();
      video.setTitle("测试标题");
      video.setDescription("测试描述");
      video.setMediaId(uploadMediaRes.getMediaId());
      WxMassUploadResult uploadResult = wxService.massVideoUpload(video);
      Assert.assertNotNull(uploadResult);
      Assert.assertNotNull(uploadResult.getMediaId());
      messages[0] = new Object[] { WxConsts.MASS_MSG_VIDEO, uploadResult.getMediaId() };
    }
    /**
     * 图片素材
     */
    {
      InputStream inputStream = ClassLoader.getSystemResourceAsStream("mm.jpeg");
      WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
      Assert.assertNotNull(uploadMediaRes);
      Assert.assertNotNull(uploadMediaRes.getMediaId());
      messages[1] = new Object[] { WxConsts.MASS_MSG_IMAGE, uploadMediaRes.getMediaId() };
    }
    /**
     * 语音素材
     */
    {
      InputStream inputStream = ClassLoader.getSystemResourceAsStream("mm.mp3");
      WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, inputStream);
      Assert.assertNotNull(uploadMediaRes);
      Assert.assertNotNull(uploadMediaRes.getMediaId());
      messages[2] = new Object[] { WxConsts.MASS_MSG_VOICE, uploadMediaRes.getMediaId() };
    }
    /**
     * 图文素材
     */
    {
      // 上传照片到媒体库
      InputStream inputStream = ClassLoader.getSystemResourceAsStream("mm.jpeg");
      WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
      Assert.assertNotNull(uploadMediaRes);
      Assert.assertNotNull(uploadMediaRes.getMediaId());
      
      // 上传图文消息
      WxMassNews news = new WxMassNews();
      WxMassNewsArticle article1 = new WxMassNewsArticle();
      article1.setTitle("标题1");
      article1.setContent("内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1");
      article1.setThumbMediaId(uploadMediaRes.getMediaId());
      news.addArticle(article1);
      
      WxMassNewsArticle article2 = new WxMassNewsArticle();
      article2.setTitle("标题2");
      article2.setContent("内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2");
      article2.setThumbMediaId(uploadMediaRes.getMediaId());
      article2.setShowCoverPic(true);
      article2.setAuthor("作者2");
      article2.setContentSourceUrl("www.baidu.com");
      article2.setDigest("摘要2");
      news.addArticle(article2);
      
      WxMassUploadResult massUploadResult = wxService.massNewsUpload(news);
      Assert.assertNotNull(massUploadResult);
      Assert.assertNotNull(uploadMediaRes.getMediaId());
      messages[3] = new Object[] { WxConsts.MASS_MSG_NEWS, massUploadResult.getMediaId() };
    }
    return messages;
  }
  
}

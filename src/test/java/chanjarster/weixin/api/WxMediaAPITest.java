package chanjarster.weixin.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.bean.result.WxMediaUploadResult;
import chanjarster.weixin.exception.WxErrorException;

import com.google.inject.Inject;

/**
 * 测试多媒体文件上传下载
 * @author chanjarster
 *
 */
@Test(groups="mediaAPI", dependsOnGroups="baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxMediaAPITest {

  @Inject
  protected WxServiceImpl wxService;

  private List<String> media_ids = new ArrayList<String>();
  
  @Test(dataProvider="uploadMedia")
  public void testUploadMedia(String mediaType, String fileType, String fileName) throws WxErrorException, IOException {
    InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
    WxMediaUploadResult res = wxService.mediaUpload(mediaType, fileType, inputStream);
    Assert.assertNotNull(res.getType());
    Assert.assertNotNull(res.getCreated_at());
    Assert.assertTrue(res.getMedia_id() != null || res.getThumb_media_id() != null);
    
    if (res.getMedia_id() != null) {
      media_ids.add(res.getMedia_id());
    }
    if (res.getThumb_media_id() != null) {
      media_ids.add(res.getThumb_media_id());
    }
  }
  
  @DataProvider
  public Object[][] uploadMedia() {
    return new Object[][] {
        new Object[] { WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, "mm.jpeg" },
        new Object[] { WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, "mm.mp3" },
        new Object[] { WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, "mm.mp4" },
        new Object[] { WxConsts.MEDIA_THUMB, WxConsts.FILE_JPG, "mm.jpeg" }
    };
  }
  
  @Test(dependsOnMethods = { "testUploadMedia" }, dataProvider="downloadMedia")
  public void testDownloadMedia(String media_id) throws WxErrorException {
    File file = wxService.mediaDownload(media_id);
    Assert.assertNotNull(file);
  }
  
  @DataProvider
  public Object[][] downloadMedia() {
    Object[][] params = new Object[this.media_ids.size()][];
    for (int i = 0; i < this.media_ids.size(); i++) {
      params[i] = new Object[] { this.media_ids.get(i) };
    }
    return params;
  }
  
}

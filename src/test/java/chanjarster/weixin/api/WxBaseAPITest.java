package chanjarster.weixin.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import chanjarster.weixin.bean.result.WxMediaUploadResult;
import chanjarster.weixin.exception.WxErrorException;

import com.google.inject.Inject;

@Test(groups="baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxBaseAPITest {

  @Inject
  protected WxServiceImpl wxService;

  private List<String> media_ids = new ArrayList<String>();
  
  @Test
  public void testRefreshAccessToken() throws WxErrorException {
    WxConfigStorage configStorage = wxService.wxConfigStorage;
    String before = configStorage.getAccessToken();
    wxService.refreshAccessToken();
    
    String after = configStorage.getAccessToken();
    
    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }
  
  @Test(dependsOnMethods = { "testRefreshAccessToken" }, dataProvider="uploadMedia", enabled = true)
  public void testUploadMedia(String mediaType, String fileType, String fileName) throws WxErrorException, IOException {
    InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
    WxMediaUploadResult res = wxService.uploadMedia(mediaType, fileType, inputStream);
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
    wxService.downloadMedia(media_id);
  }
  
  @DataProvider
  public Object[][] downloadMedia() {
    Object[][] params = new Object[this.media_ids.size()][];
    for (int i = 0; i < this.media_ids.size(); i++) {
      params[i] = new Object[] { this.media_ids.get(i) };
    }
    return params;
  }
  
  @Test(enabled = true)
  public void testCheckSignature() throws WxErrorException {
    String timestamp = "23234235423246";
    String nonce = "y7didfkcmvnbd90sdofjkiefhsd";
    String signature = "77b6651628dfb9a64bfb0d3432ee053ac566a459";
    Assert.assertTrue(wxService.checkSignature(timestamp, nonce, signature));
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

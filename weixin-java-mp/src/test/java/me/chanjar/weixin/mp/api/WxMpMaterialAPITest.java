package me.chanjar.weixin.mp.api;

import com.google.inject.Inject;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.mp.bean.WxMpMaterial;
import me.chanjar.weixin.mp.bean.WxMpMaterialArticleUpdate;
import me.chanjar.weixin.mp.bean.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.result.*;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 测试多媒体文件上传下载
 *
 * @author codepiano
 */
@Test(groups = "materialAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpMaterialAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  private Map<String, Map<String, Object>> media_ids = new LinkedHashMap<>();
  // 缩略图的id，测试上传图文使用
  private String thumbMediaId = "";
  // 单图文消息media_id
  private String singleNewsMediaId = "";
  // 多图文消息media_id
  private String multiNewsMediaId = "";
  // 先查询保存测试开始前永久素材数据
  private WxMpMaterialCountResult wxMaterialCountResultBeforeTest;

  @Test(dataProvider = "uploadMaterial")
  public void testUploadMaterial(String mediaType, String fileType, String fileName) throws WxErrorException, IOException {
    if (wxMaterialCountResultBeforeTest == null) {
      wxMaterialCountResultBeforeTest = wxService.materialCount();
    }
    InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
    File tempFile = FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType);
    WxMpMaterial wxMaterial = new WxMpMaterial();
    wxMaterial.setFile(tempFile);
    wxMaterial.setName(fileName);
    if (WxConsts.MEDIA_VIDEO.equals(mediaType)) {
      wxMaterial.setVideoTitle("title");
      wxMaterial.setVideoIntroduction("test video description");
    }
    WxMpMaterialUploadResult res = wxService.materialFileUpload(mediaType, wxMaterial);
    Assert.assertNotNull(res.getMediaId());
    if (WxConsts.MEDIA_IMAGE.equals(mediaType) || WxConsts.MEDIA_THUMB.equals(mediaType)) {
      Assert.assertNotNull(res.getUrl());
    }
    if (WxConsts.MEDIA_THUMB.equals(mediaType)) {
      thumbMediaId = res.getMediaId();
    }

    Map<String, Object> materialInfo = new HashMap<>();
    materialInfo.put("media_id", res.getMediaId());
    materialInfo.put("length", tempFile.length());
    materialInfo.put("filename", tempFile.getName());
    media_ids.put(res.getMediaId(), materialInfo);
  }

  @Test(dependsOnMethods = {"testUploadMaterial"})
  public void testAddNews() throws WxErrorException {

    // 单图文消息
    WxMpMaterialNews wxMpMaterialNewsSingle = new WxMpMaterialNews();
    WxMpMaterialNews.WxMpMaterialNewsArticle mpMaterialNewsArticleSingle = new WxMpMaterialNews.WxMpMaterialNewsArticle();
    mpMaterialNewsArticleSingle.setAuthor("author");
    mpMaterialNewsArticleSingle.setThumbMediaId(thumbMediaId);
    mpMaterialNewsArticleSingle.setTitle("single title");
    mpMaterialNewsArticleSingle.setContent("single content");
    mpMaterialNewsArticleSingle.setContentSourceUrl("content url");
    mpMaterialNewsArticleSingle.setShowCoverPic(true);
    mpMaterialNewsArticleSingle.setDigest("single news");
    wxMpMaterialNewsSingle.addArticle(mpMaterialNewsArticleSingle);

    // 多图文消息
    WxMpMaterialNews wxMpMaterialNewsMultiple = new WxMpMaterialNews();
    WxMpMaterialNews.WxMpMaterialNewsArticle wxMpMaterialNewsArticleMutiple1 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
    wxMpMaterialNewsArticleMutiple1.setAuthor("author1");
    wxMpMaterialNewsArticleMutiple1.setThumbMediaId(thumbMediaId);
    wxMpMaterialNewsArticleMutiple1.setTitle("multi title1");
    wxMpMaterialNewsArticleMutiple1.setContent("content 1");
    wxMpMaterialNewsArticleMutiple1.setContentSourceUrl("content url");
    wxMpMaterialNewsArticleMutiple1.setShowCoverPic(true);
    wxMpMaterialNewsArticleMutiple1.setDigest("");

    WxMpMaterialNews.WxMpMaterialNewsArticle wxMpMaterialNewsArticleMultiple2 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
    wxMpMaterialNewsArticleMultiple2.setAuthor("author2");
    wxMpMaterialNewsArticleMultiple2.setThumbMediaId(thumbMediaId);
    wxMpMaterialNewsArticleMultiple2.setTitle("multi title2");
    wxMpMaterialNewsArticleMultiple2.setContent("content 2");
    wxMpMaterialNewsArticleMultiple2.setContentSourceUrl("content url");
    wxMpMaterialNewsArticleMultiple2.setShowCoverPic(true);
    wxMpMaterialNewsArticleMultiple2.setDigest("");

    wxMpMaterialNewsMultiple.addArticle(wxMpMaterialNewsArticleMutiple1);
    wxMpMaterialNewsMultiple.addArticle(wxMpMaterialNewsArticleMultiple2);

    WxMpMaterialUploadResult resSingle = wxService.materialNewsUpload(wxMpMaterialNewsSingle);
    singleNewsMediaId = resSingle.getMediaId();
    WxMpMaterialUploadResult resMulti = wxService.materialNewsUpload(wxMpMaterialNewsMultiple);
    multiNewsMediaId = resMulti.getMediaId();
  }

  @Test(dependsOnMethods = {"testAddNews"})
  public void testMaterialCount() throws WxErrorException {
    WxMpMaterialCountResult wxMaterialCountResult = wxService.materialCount();
    // 测试上传过程中添加了一个音频，一个视频，两个图片，两个图文消息
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getVoiceCount() + 1, wxMaterialCountResult.getVoiceCount());
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getVideoCount() + 1, wxMaterialCountResult.getVideoCount());
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getImageCount() + 2, wxMaterialCountResult.getImageCount());
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getNewsCount() + 2, wxMaterialCountResult.getNewsCount());
  }


  @DataProvider
  public Object[][] uploadMaterial() {
    return new Object[][]{
        new Object[]{WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, "mm.jpeg"},
        new Object[]{WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, "mm.mp3"},
        new Object[]{WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, "mm.mp4"},
        new Object[]{WxConsts.MEDIA_THUMB, WxConsts.FILE_JPG, "mm.jpeg"}
    };
  }

  @Test(dependsOnMethods = {"testMaterialCount"}, dataProvider = "downloadMaterial")
  public void testDownloadMaterial(String media_id) throws WxErrorException, IOException {
    Map<String, Object> materialInfo = media_ids.get(media_id);
    Assert.assertNotNull(materialInfo);
    String filename = materialInfo.get("filename").toString();
    if (filename.endsWith(".mp3") || filename.endsWith(".jpeg")) {
      InputStream inputStream = wxService.materialImageOrVoiceDownload(media_id);
      Assert.assertNotNull(inputStream);
      IOUtils.closeQuietly(inputStream);
    }
    if (filename.endsWith("mp4")) {
      WxMpMaterialVideoInfoResult wxMaterialVideoInfoResult = wxService.materialVideoInfo(media_id);
      Assert.assertNotNull(wxMaterialVideoInfoResult);
      Assert.assertNotNull(wxMaterialVideoInfoResult.getDownUrl());
    }
  }

  @Test(dependsOnMethods = {"testAddNews"})
  public void testGetNewsInfo() throws WxErrorException {
    WxMpMaterialNews wxMpMaterialNewsSingle = wxService.materialNewsInfo(singleNewsMediaId);
    WxMpMaterialNews wxMpMaterialNewsMultiple = wxService.materialNewsInfo(multiNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsSingle);
    Assert.assertNotNull(wxMpMaterialNewsMultiple);
  }

  @Test(dependsOnMethods = {"testGetNewsInfo"})
  public void testUpdateNewsInfo() throws WxErrorException {
    WxMpMaterialNews wxMpMaterialNewsSingle = wxService.materialNewsInfo(singleNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsSingle);
    WxMpMaterialArticleUpdate wxMpMaterialArticleUpdateSingle = new WxMpMaterialArticleUpdate();
    WxMpMaterialNews.WxMpMaterialNewsArticle articleSingle = wxMpMaterialNewsSingle.getArticles().get(0);
    articleSingle.setContent("content single update");
    wxMpMaterialArticleUpdateSingle.setMediaId(singleNewsMediaId);
    wxMpMaterialArticleUpdateSingle.setArticles(articleSingle);
    wxMpMaterialArticleUpdateSingle.setIndex(0);
    boolean resultSingle = wxService.materialNewsUpdate(wxMpMaterialArticleUpdateSingle);
    Assert.assertTrue(resultSingle);
    wxMpMaterialNewsSingle = wxService.materialNewsInfo(singleNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsSingle);
    Assert.assertEquals("content single update", wxMpMaterialNewsSingle.getArticles().get(0).getContent());

    WxMpMaterialNews wxMpMaterialNewsMultiple = wxService.materialNewsInfo(multiNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsMultiple);
    WxMpMaterialArticleUpdate wxMpMaterialArticleUpdateMulti = new WxMpMaterialArticleUpdate();
    WxMpMaterialNews.WxMpMaterialNewsArticle articleMulti = wxMpMaterialNewsMultiple.getArticles().get(1);
    articleMulti.setContent("content 2 update");
    wxMpMaterialArticleUpdateMulti.setMediaId(multiNewsMediaId);
    wxMpMaterialArticleUpdateMulti.setArticles(articleMulti);
    wxMpMaterialArticleUpdateMulti.setIndex(1);
    boolean resultMulti = wxService.materialNewsUpdate(wxMpMaterialArticleUpdateMulti);
    Assert.assertTrue(resultMulti);
    wxMpMaterialNewsMultiple = wxService.materialNewsInfo(multiNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsMultiple);
    Assert.assertEquals("content 2 update", wxMpMaterialNewsMultiple.getArticles().get(1).getContent());
  }


  @Test(dependsOnMethods = {"testUpdateNewsInfo"})
  public void testMaterialNewsList() throws WxErrorException {
    WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = wxService.materialNewsBatchGet(0, 20);
    return;
  }

  @Test(dependsOnMethods = {"testMaterialNewsList"})
  public void testMaterialFileList() throws WxErrorException {
    WxMpMaterialFileBatchGetResult wxMpMaterialVoiceBatchGetResult = wxService.materialFileBatchGet(WxConsts.MATERIAL_VOICE, 0, 20);
    WxMpMaterialFileBatchGetResult wxMpMaterialVideoBatchGetResult = wxService.materialFileBatchGet(WxConsts.MATERIAL_VIDEO, 0, 20);
    WxMpMaterialFileBatchGetResult wxMpMaterialImageBatchGetResult = wxService.materialFileBatchGet(WxConsts.MATERIAL_IMAGE, 0, 20);
    return;
  }

  @Test(dependsOnMethods = {"testMaterialFileList"}, dataProvider = "allTestMaterial")
  public void testDeleteMaterial(String mediaId) throws WxErrorException {
    boolean result = wxService.materialDelete(mediaId);
    Assert.assertTrue(result);
  }

  @DataProvider
  public Object[][] downloadMaterial() {
    Object[][] params = new Object[this.media_ids.size()][];
    int index = 0;
    for (String mediaId : this.media_ids.keySet()) {
      params[index] = new Object[]{mediaId};
      index++;
    }
    return params;
  }

  @DataProvider
  public Iterator<Object[]> allTestMaterial() {
    List<Object[]> params = new ArrayList<>();
    for (String mediaId : this.media_ids.keySet()) {
      params.add(new Object[]{mediaId});
    }
    params.add(new Object[]{this.singleNewsMediaId});
    params.add(new Object[]{this.multiNewsMediaId});
    return params.iterator();
  }
}

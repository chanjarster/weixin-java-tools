package me.chanjar.weixin.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

import me.chanjar.weixin.api.WxConsts;
import me.chanjar.weixin.bean.WxCustomMessage.WxArticle;

@Test
public class WxCustomMessageTest {

  public void testTextReply() {
    WxCustomMessage reply = new WxCustomMessage();
    reply.setToUser("OPENID");
    reply.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
    reply.setContent("sfsfdsdf");
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"text\",\"text\":{\"content\":\"sfsfdsdf\"}}");
  }
  
  public void testTextBuild() {
    WxCustomMessage reply = WxCustomMessage.TEXT().toUser("OPENID").content("sfsfdsdf").build();
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"text\",\"text\":{\"content\":\"sfsfdsdf\"}}");
  }
  
  public void testImageReply() {
    WxCustomMessage reply = new WxCustomMessage();
    reply.setToUser("OPENID");
    reply.setMsgType(WxConsts.CUSTOM_MSG_IMAGE);
    reply.setMediaId("MEDIA_ID");
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"MEDIA_ID\"}}");
  }
  
  public void testImageBuild() {
    WxCustomMessage reply = WxCustomMessage.IMAGE().toUser("OPENID").mediaId("MEDIA_ID").build();
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"MEDIA_ID\"}}");
  }
  
  public void testVoiceReply() {
    WxCustomMessage reply = new WxCustomMessage();
    reply.setToUser("OPENID");
    reply.setMsgType(WxConsts.CUSTOM_MSG_VOICE);
    reply.setMediaId("MEDIA_ID");
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"MEDIA_ID\"}}");
  }
  
  public void testVoiceBuild() {
    WxCustomMessage reply = WxCustomMessage.VOICE().toUser("OPENID").mediaId("MEDIA_ID").build();
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"MEDIA_ID\"}}");
  }
  
  public void testVideoReply() {
    WxCustomMessage reply = new WxCustomMessage();
    reply.setToUser("OPENID");
    reply.setMsgType(WxConsts.CUSTOM_MSG_VIDEO);
    reply.setMediaId("MEDIA_ID");
    reply.setThumbMediaId("MEDIA_ID");
    reply.setTitle("TITLE");
    reply.setDescription("DESCRIPTION");
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"MEDIA_ID\",\"thumb_media_id\":\"MEDIA_ID\",\"title\":\"TITLE\",\"description\":\"DESCRIPTION\"}}");
  }
  
  public void testVideoBuild() {
    WxCustomMessage reply = WxCustomMessage.VIDEO().toUser("OPENID").title("TITLE").mediaId("MEDIA_ID").thumbMediaId("MEDIA_ID").description("DESCRIPTION").build();
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"MEDIA_ID\",\"thumb_media_id\":\"MEDIA_ID\",\"title\":\"TITLE\",\"description\":\"DESCRIPTION\"}}");
  }
  
  public void testMusicReply() {
    WxCustomMessage reply = new WxCustomMessage();
    reply.setToUser("OPENID");
    reply.setMsgType(WxConsts.CUSTOM_MSG_MUSIC);
    reply.setThumbMediaId("MEDIA_ID");
    reply.setDescription("DESCRIPTION");
    reply.setTitle("TITLE");
    reply.setMusicUrl("MUSIC_URL");
    reply.setHqMusicUrl("HQ_MUSIC_URL");
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"music\",\"music\":{\"title\":\"TITLE\",\"description\":\"DESCRIPTION\",\"thumb_media_id\":\"MEDIA_ID\",\"musicurl\":\"MUSIC_URL\",\"hqmusicurl\":\"HQ_MUSIC_URL\"}}");
  }
  
  public void testMusicBuild() {
    WxCustomMessage reply = WxCustomMessage.MUSIC()
          .toUser("OPENID")
          .title("TITLE")
          .thumbMediaId("MEDIA_ID")
          .description("DESCRIPTION")
          .musicUrl("MUSIC_URL")
          .hqMusicUrl("HQ_MUSIC_URL")
          .build();
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"music\",\"music\":{\"title\":\"TITLE\",\"description\":\"DESCRIPTION\",\"thumb_media_id\":\"MEDIA_ID\",\"musicurl\":\"MUSIC_URL\",\"hqmusicurl\":\"HQ_MUSIC_URL\"}}");
  }
  
  public void testNewsReply() {
    WxCustomMessage reply = new WxCustomMessage();
    reply.setToUser("OPENID");
    reply.setMsgType(WxConsts.CUSTOM_MSG_NEWS);
    
    WxArticle article1 = new WxArticle();
    article1.setUrl("URL");
    article1.setPicUrl("PIC_URL");
    article1.setDescription("Is Really A Happy Day");
    article1.setTitle("Happy Day");
    reply.getArticles().add(article1);
    
    WxArticle article2 = new WxArticle();
    article2.setUrl("URL");
    article2.setPicUrl("PIC_URL");
    article2.setDescription("Is Really A Happy Day");
    article2.setTitle("Happy Day");
    reply.getArticles().add(article2);

    
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"news\",\"articles\":[{\"title\":\"Happy Day\",\"description\":\"Is Really A Happy Day\",\"url\":\"URL\",\"picurl\":\"PIC_URL\"},{\"title\":\"Happy Day\",\"description\":\"Is Really A Happy Day\",\"url\":\"URL\",\"picurl\":\"PIC_URL\"}]}");
  }
  
  public void testNewsBuild() {
    WxArticle article1 = new WxArticle();
    article1.setUrl("URL");
    article1.setPicUrl("PIC_URL");
    article1.setDescription("Is Really A Happy Day");
    article1.setTitle("Happy Day");
    
    WxArticle article2 = new WxArticle();
    article2.setUrl("URL");
    article2.setPicUrl("PIC_URL");
    article2.setDescription("Is Really A Happy Day");
    article2.setTitle("Happy Day");

    WxCustomMessage reply = WxCustomMessage.NEWS().toUser("OPENID").addArticle(article1).addArticle(article2).build();
    
    Assert.assertEquals(reply.toJson(), "{\"touser\":\"OPENID\",\"msgtype\":\"news\",\"articles\":[{\"title\":\"Happy Day\",\"description\":\"Is Really A Happy Day\",\"url\":\"URL\",\"picurl\":\"PIC_URL\"},{\"title\":\"Happy Day\",\"description\":\"Is Really A Happy Day\",\"url\":\"URL\",\"picurl\":\"PIC_URL\"}]}");
  }
  
}

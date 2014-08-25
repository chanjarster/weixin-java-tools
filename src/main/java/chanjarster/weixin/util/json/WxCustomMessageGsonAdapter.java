/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package chanjarster.weixin.util.json;

import java.lang.reflect.Type;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxCustomMessage.WxArticle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author qianjia
 *
 */
public class WxCustomMessageGsonAdapter implements JsonSerializer<WxCustomMessage> {

  public JsonElement serialize(WxCustomMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    messageJson.addProperty("touser", message.getTouser());
    messageJson.addProperty("msgtype", message.getMsgtype());
    
    if (WxConsts.CUSTOM_MSG_TEXT.equals(message.getMsgtype())) {
      JsonObject text = new JsonObject();
      text.addProperty("content", message.getContent());
      messageJson.add("text", text);
    }

    if (WxConsts.CUSTOM_MSG_IMAGE.equals(message.getMsgtype())) {
      JsonObject image = new JsonObject();
      image.addProperty("media_id", message.getMedia_id());
      messageJson.add("image", image);
    }

    if (WxConsts.CUSTOM_MSG_VOICE.equals(message.getMsgtype())) {
      JsonObject voice = new JsonObject();
      voice.addProperty("media_id", message.getMedia_id());
      messageJson.add("voice", voice);
    }

    if (WxConsts.CUSTOM_MSG_VIDEO.equals(message.getMsgtype())) {
      JsonObject video = new JsonObject();
      video.addProperty("media_id", message.getMedia_id());
      video.addProperty("thumb_media_id", message.getThumb_media_id());
      video.addProperty("title", message.getTitle());
      video.addProperty("description", message.getDescription());
      messageJson.add("video", video);
    }

    if (WxConsts.CUSTOM_MSG_MUSIC.equals(message.getMsgtype())) {
      JsonObject music = new JsonObject();
      music.addProperty("title", message.getTitle());
      music.addProperty("description", message.getDescription());
      music.addProperty("thumb_media_id", message.getThumb_media_id());
      music.addProperty("musicurl", message.getMusicurl());
      music.addProperty("hqmusicurl", message.getHqmusicurl());
      messageJson.add("music", music);
    }
    
    if (WxConsts.CUSTOM_MSG_NEWS.equals(message.getMsgtype())) {
      JsonArray articleJsonArray = new JsonArray();
      for (WxArticle article : message.getArticles()) {
        JsonObject articleJson = new JsonObject();
        articleJson.addProperty("title", article.getTitle());
        articleJson.addProperty("description", article.getDescription());
        articleJson.addProperty("url", article.getUrl());
        articleJson.addProperty("picurl", article.getPicurl());
        articleJsonArray.add(articleJson);
      }
      messageJson.add("articles", articleJsonArray);
    }
    
    return messageJson;
  }

}

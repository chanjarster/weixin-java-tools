/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package me.chanjar.weixin.mp.util.json;

import com.google.gson.*;
import me.chanjar.weixin.mp.bean.WxMpMassNews;

import java.lang.reflect.Type;

public class WxMpMassNewsGsonAdapter implements JsonSerializer<WxMpMassNews> {

  public JsonElement serialize(WxMpMassNews message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject newsJson = new JsonObject();
    
    JsonArray articleJsonArray = new JsonArray();
    for (WxMpMassNews.WxMpMassNewsArticle article : message.getArticles()) {
      JsonObject articleJson = new JsonObject();
      articleJson.addProperty("thumb_media_id", article.getThumbMediaId());
      articleJson.addProperty("title", article.getTitle());
      articleJson.addProperty("content", article.getContent());

      if (null != article.getAuthor()) {
        articleJson.addProperty("author", article.getAuthor());
      }
      if (null != article.getContentSourceUrl()) {
        articleJson.addProperty("content_source_url", article.getContentSourceUrl());
      }
      if (null != article.getDigest()) {
        articleJson.addProperty("digest", article.getDigest());
      }
      articleJson.addProperty("show_cover_pic", article.isShowCoverPic() ? "1" : "0");
      articleJsonArray.add(articleJson);
    }
    newsJson.add("articles", articleJsonArray);
    
    return newsJson;
  }

}

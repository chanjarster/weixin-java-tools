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

import chanjarster.weixin.bean.WxMassNews;
import chanjarster.weixin.bean.WxMassNews.WxMassNewsArticle;

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
public class WxMassNewsGsonAdapter implements JsonSerializer<WxMassNews> {

  public JsonElement serialize(WxMassNews message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject newsJson = new JsonObject();
    
    JsonArray articleJsonArray = new JsonArray();
    for (WxMassNewsArticle article : message.getArticles()) {
      JsonObject articleJson = new JsonObject();
      articleJson.addProperty("thumb_media_id", article.getThumb_media_id());
      articleJson.addProperty("title", article.getTitle());
      articleJson.addProperty("content", article.getContent());

      if (null != article.getAuthor()) {
        articleJson.addProperty("author", article.getAuthor());
      }
      if (null != article.getContent_source_url()) {
        articleJson.addProperty("content_source_url", article.getContent_source_url());
      }
      if (null != article.getDigest()) {
        articleJson.addProperty("digest", article.getDigest());
      }
      articleJson.addProperty("show_cover_pic", article.isShow_cover_pic() ? "1" : "0");
      articleJsonArray.add(articleJson);
    }
    newsJson.add("articles", articleJsonArray);
    
    return newsJson;
  }

}

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
import chanjarster.weixin.bean.WxMassMessage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author qianjia
 *
 */
public class WxMassMessageGsonAdapter implements JsonSerializer<WxMassMessage> {

  public JsonElement serialize(WxMassMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    
    if (message.getTouser().size() > 0) {
      JsonArray sub = new JsonArray();
      for(String openId : message.getTouser()) {
        sub.add(new JsonPrimitive(openId));
      }
      messageJson.add("touser", sub);
    } else {
      JsonObject sub = new JsonObject();
      sub.addProperty("group_id", message.getGroup_id());
      messageJson.add("filter", sub);
    }
    
    if (WxConsts.MASS_MSG_NEWS.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add("mpnews", sub);
    }
    if (WxConsts.MASS_MSG_TEXT.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("content", message.getContent());
      messageJson.add("text", sub);
    }
    if (WxConsts.MASS_MSG_VOICE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add("voice", sub);
    }
    if (WxConsts.MASS_MSG_IMAGE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add("image", sub);
    }
    if (WxConsts.MASS_MSG_VIDEO.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add("mpvideo", sub);
    }
    messageJson.addProperty("msgtype", message.getMsgtype());
    return messageJson;
  }

}

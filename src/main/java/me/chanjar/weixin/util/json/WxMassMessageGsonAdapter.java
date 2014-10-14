/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package me.chanjar.weixin.util.json;

import java.lang.reflect.Type;

import me.chanjar.weixin.api.WxConsts;
import me.chanjar.weixin.bean.WxMassGroupMessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author qianjia
 *
 */
public class WxMassMessageGsonAdapter implements JsonSerializer<WxMassGroupMessage> {

  public JsonElement serialize(WxMassGroupMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    
    JsonObject filter = new JsonObject();
    filter.addProperty("group_id", message.getGroup_id());
    messageJson.add("filter", filter);
    
    if (WxConsts.MASS_MSG_NEWS.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add(WxConsts.MASS_MSG_NEWS, sub);
    }
    if (WxConsts.MASS_MSG_TEXT.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("content", message.getContent());
      messageJson.add(WxConsts.MASS_MSG_TEXT, sub);
    }
    if (WxConsts.MASS_MSG_VOICE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add(WxConsts.MASS_MSG_VOICE, sub);
    }
    if (WxConsts.MASS_MSG_IMAGE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add(WxConsts.MASS_MSG_IMAGE, sub);
    }
    if (WxConsts.MASS_MSG_VIDEO.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMedia_id());
      messageJson.add(WxConsts.MASS_MSG_VIDEO, sub);
    }
    messageJson.addProperty("msgtype", message.getMsgtype());
    return messageJson;
  }

}

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

import chanjarster.weixin.bean.WxGroup;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author qianjia
 *
 */
public class WxGroupGsonAdapter implements JsonSerializer<WxGroup>, JsonDeserializer<WxGroup> {

  public JsonElement serialize(WxGroup group, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    JsonObject groupJson = new JsonObject();
    groupJson.addProperty("name", group.getName());
    groupJson.addProperty("id", group.getId());
    groupJson.addProperty("count", group.getCount());
    json.add("group", groupJson);
    return json;
  }

  public WxGroup deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    WxGroup group = new WxGroup();
    JsonObject groupJson = json.getAsJsonObject();
    if (json.getAsJsonObject().get("group") != null) {
      groupJson = json.getAsJsonObject().get("group").getAsJsonObject();
    }
    if (groupJson.get("name") != null && !groupJson.get("name").isJsonNull()) {
      group.setName(GsonHelper.getAsString(groupJson.get("name")));
    }
    if (groupJson.get("id") != null && !groupJson.get("id").isJsonNull()) {
      group.setId(GsonHelper.getAsPrimitiveLong(groupJson.get("id")));
    }
    if (groupJson.get("count") != null && !groupJson.get("count").isJsonNull()) {
      group.setCount(GsonHelper.getAsPrimitiveLong(groupJson.get("count")));
    }
    return group;
  }
  
}
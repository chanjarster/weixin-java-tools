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

import chanjarster.weixin.bean.result.WxUserList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * 
 * @author qianjia
 *
 */
public class WxUserListGsonAdapter implements JsonDeserializer<WxUserList> {

  public WxUserList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    WxUserList wxUserList = new WxUserList();
    wxUserList.setTotal(GsonHelper.getInteger(o, "total"));
    wxUserList.setCount(GsonHelper.getInteger(o, "count"));
    wxUserList.setNext_openid(GsonHelper.getString(o, "next_openid"));
    JsonArray data = o.get("data").getAsJsonObject().get("openid").getAsJsonArray();
    for (int i = 0; i < data.size(); i++) {
      wxUserList.getOpenids().add(GsonHelper.getAsString(data.get(i)));
    }
    return wxUserList;
  }

}
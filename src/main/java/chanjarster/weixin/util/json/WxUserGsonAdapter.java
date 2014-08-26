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

import chanjarster.weixin.bean.result.WxUser;

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
public class WxUserGsonAdapter implements JsonDeserializer<WxUser> {

  public WxUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    WxUser wxUser = new WxUser();
    wxUser.setSubscribe(new Integer(0).equals(GsonHelper.getInteger(o, "subscribe")) ? false : true);
    wxUser.setCity(GsonHelper.getString(o, "city"));
    wxUser.setCountry(GsonHelper.getString(o, "country"));
    wxUser.setHeadimgurl(GsonHelper.getString(o, "headimgurl"));
    wxUser.setLanguage(GsonHelper.getString(o, "language"));
    wxUser.setNickname(GsonHelper.getString(o, "nickname"));
    wxUser.setOpenid(GsonHelper.getString(o, "openid"));
    wxUser.setProvince(GsonHelper.getString(o, "province"));
    wxUser.setSubscribe_time(GsonHelper.getLong(o, "subscribe_time"));
    wxUser.setUnionid(GsonHelper.getString(o, "unionid"));
    Integer sex = GsonHelper.getInteger(o, "sex");
    if(new Integer(1).equals(sex)) {
      wxUser.setSex("男");
    } else if (new Integer(2).equals(sex)) {
      wxUser.setSex("女");
    } else {
      wxUser.setSex("未知");
    }
    return wxUser;
  }

}
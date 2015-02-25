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
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Daniel Qian
 */
public class WxMpUserSummaryGsonAdapter implements JsonDeserializer<WxMpUserSummary> {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public WxMpUserSummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    WxMpUserSummary summary = new WxMpUserSummary();
    JsonObject summaryJsonObject = json.getAsJsonObject();

    try {
      String refDate = GsonHelper.getString(summaryJsonObject, "ref_date");
      if (refDate != null) {
        summary.setRefDate(SIMPLE_DATE_FORMAT.parse(refDate));
      }
      summary.setUserSource(GsonHelper.getInteger(summaryJsonObject, "user_source"));
      summary.setNewUser(GsonHelper.getInteger(summaryJsonObject, "new_user"));
      summary.setCancelUser(GsonHelper.getInteger(summaryJsonObject, "cancel_user"));
    } catch (ParseException e) {
      throw new JsonParseException(e);
    }

    return summary;
  }

}

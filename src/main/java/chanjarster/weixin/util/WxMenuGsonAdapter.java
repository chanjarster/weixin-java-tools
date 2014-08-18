/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package chanjarster.weixin.util;

import java.lang.reflect.Type;

import chanjarster.weixin.out.WxMenu;
import chanjarster.weixin.out.WxMenu.WxMenuButton;

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
public class WxMenuGsonAdapter implements JsonSerializer<WxMenu> {

  public JsonElement serialize(WxMenu menu, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();

    JsonArray buttonArray = new JsonArray();
    for (WxMenuButton button : menu.getButton()) {
      JsonObject buttonJson = serialize(button);
      buttonArray.add(buttonJson);
    }
    json.add("button", buttonArray);
    
    return json;
  }

  protected JsonObject serialize(WxMenuButton button) {
    JsonObject buttonJson = new JsonObject();
    buttonJson.addProperty("name", button.getName());
    // TODO 其他字段
    if (button.getSub_button() == null || button.getSub_button().size() == 0) {
      JsonArray buttonArray = new JsonArray();
      for (WxMenuButton sub_button : button.getSub_button()) {
        buttonArray.add(serialize(sub_button));
      }
      buttonJson.add("sub_button", buttonArray);
    }
    return buttonJson;
  }
}

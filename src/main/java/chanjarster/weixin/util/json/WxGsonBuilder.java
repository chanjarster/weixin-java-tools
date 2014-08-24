package chanjarster.weixin.util.json;

import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxMenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCustomMessage.class, new WxCustomMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}

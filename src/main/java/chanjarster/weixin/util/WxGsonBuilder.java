package chanjarster.weixin.util;

import chanjarster.weixin.out.WxCustomMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCustomMessage.class, new WxCustomMessageGsonAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}

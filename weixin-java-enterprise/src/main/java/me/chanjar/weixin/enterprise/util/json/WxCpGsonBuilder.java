package me.chanjar.weixin.enterprise.util.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.chanjar.weixin.enterprise.bean.*;
import me.chanjar.weixin.enterprise.bean.result.*;

public class WxCpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCpMessage.class, new WxCpMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMenu.class, new WxCpMenuGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
    INSTANCE.registerTypeAdapter(WxUser.class, new WxCpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxUserList.class, new WxCpUserListGsonAdapter());
    INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxCpAccessTokenAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxCpMediaUploadResultAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}

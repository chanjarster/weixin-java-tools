package me.chanjar.weixin.util.json;

import me.chanjar.weixin.bean.WxCustomMessage;
import me.chanjar.weixin.bean.WxGroup;
import me.chanjar.weixin.bean.WxMassGroupMessage;
import me.chanjar.weixin.bean.WxMassNews;
import me.chanjar.weixin.bean.WxMassOpenIdsMessage;
import me.chanjar.weixin.bean.WxMenu;
import me.chanjar.weixin.bean.result.WxUser;
import me.chanjar.weixin.bean.result.WxUserList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCustomMessage.class, new WxCustomMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMassNews.class, new WxMassNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMassGroupMessage.class, new WxMassMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMassOpenIdsMessage.class, new WxMassOpenIdsMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxGroup.class, new WxGroupGsonAdapter());
    INSTANCE.registerTypeAdapter(WxUser.class, new WxUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxUserList.class, new WxUserListGsonAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}

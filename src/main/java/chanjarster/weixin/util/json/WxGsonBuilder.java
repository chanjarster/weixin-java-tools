package chanjarster.weixin.util.json;

import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxGroup;
import chanjarster.weixin.bean.WxMassGroupMessage;
import chanjarster.weixin.bean.WxMassNews;
import chanjarster.weixin.bean.WxMassOpenIdsMessage;
import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.bean.result.WxUser;
import chanjarster.weixin.bean.result.WxUserList;

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

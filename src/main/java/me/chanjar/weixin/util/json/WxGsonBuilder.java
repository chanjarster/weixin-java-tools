package me.chanjar.weixin.util.json;

import me.chanjar.weixin.bean.*;
import me.chanjar.weixin.bean.result.*;

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
    INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxMassVideo.class, new WxMassVideoAdapter());
    INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxMassSendResult.class, new WxMassSendResultAdapter());
    INSTANCE.registerTypeAdapter(WxMassUploadResult.class, new WxMassUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxQrCodeTicket.class, new WxQrCodeTicketAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}

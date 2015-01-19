package me.chanjar.weixin.mp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.chanjar.weixin.mp.bean.*;
import me.chanjar.weixin.mp.bean.result.*;

public class WxMpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpCustomMessage.class, new WxMpCustomMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassNews.class, new WxMpMassNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassGroupMessage.class, new WxMpMassGroupMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassOpenIdsMessage.class, new WxMpMassOpenIdsMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpGroup.class, new WxMpGroupGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUser.class, new WxUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserList.class, new WxUserListGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassVideo.class, new WxMpMassVideoAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassSendResult.class, new WxMpMassSendResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassUploadResult.class, new WxMpMassUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpQrCodeTicket.class, new WxQrCodeTicketAdapter());
    INSTANCE.registerTypeAdapter(WxMpTemplateMessage.class, new WxMpTemplateMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpSemanticQueryResult.class, new WxMpSemanticQueryResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}

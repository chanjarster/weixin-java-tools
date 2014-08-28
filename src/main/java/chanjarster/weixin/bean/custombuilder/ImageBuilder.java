package chanjarster.weixin.bean.custombuilder;

import chanjarster.weixin.api.WxConsts;
import chanjarster.weixin.bean.WxCustomMessage;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.IMAGE().media_id(...).touser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
  private String media_id;

  public ImageBuilder() {
    this.msgtype = WxConsts.CUSTOM_MSG_IMAGE;
  }

  public ImageBuilder mediaId(String media_id) {
    this.media_id = media_id;
    return this;
  }

  public WxCustomMessage build() {
    WxCustomMessage m = super.build();
    m.setMedia_id(this.media_id);
    return m;
  }
}

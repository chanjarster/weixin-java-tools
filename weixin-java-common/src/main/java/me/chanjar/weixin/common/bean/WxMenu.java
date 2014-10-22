package me.chanjar.weixin.common.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;

/**
 * 企业号菜单
 * @author Daniel Qian
 *
 */
public class WxMenu {

  private List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();

  public List<WxMenuButton> getButtons() {
    return buttons;
  }

  public void setButtons(List<WxMenuButton> buttons) {
    this.buttons = buttons;
  }
  
  public String toJson() {
    return WxGsonBuilder.create().toJson(this);
  }
  
  public static WxMenu fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMenu.class);
  }
  
  public static WxMenu fromJson(InputStream is) {
    return WxGsonBuilder.create().fromJson(new InputStreamReader(is), WxMenu.class);
  }
  
  public static class WxMenuButton {

    private String type;
    private String name;
    private String key;
    private String url;
    
    private List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public List<WxMenuButton> getSubButtons() {
      return subButtons;
    }

    public void setSubButtons(List<WxMenuButton> subButtons) {
      this.subButtons = subButtons;
    }
    
  }

}

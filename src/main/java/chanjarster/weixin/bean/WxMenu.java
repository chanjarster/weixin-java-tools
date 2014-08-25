package chanjarster.weixin.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 公众号菜单
 * @author chanjarster
 *
 */
public class WxMenu {

  private List<WxMenuButton> button = new ArrayList<WxMenuButton>();

  public List<WxMenuButton> getButton() {
    return button;
  }

  public void setButton(List<WxMenuButton> button) {
    this.button = button;
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
    
    private List<WxMenuButton> sub_button = new ArrayList<WxMenuButton>();

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

    public List<WxMenuButton> getSub_button() {
      return sub_button;
    }

    public void setSub_button(List<WxMenuButton> sub_button) {
      this.sub_button = sub_button;
    }
    
  }

}

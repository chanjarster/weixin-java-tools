package me.chanjar.weixin.common.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import org.apache.commons.codec.Charsets;

/**
 * 企业号菜单
 * @author Daniel Qian
 *
 */
public class WxMenu implements Serializable {

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

  /**
   * 要用 http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html 格式来反序列化
   * 相比 http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html 的格式，外层多套了一个menu
   * @param json
   * @return
   */
  public static WxMenu fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMenu.class);
  }

  /**
   * 要用 http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html 格式来反序列化
   * 相比 http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html 的格式，外层多套了一个menu
   * @param is
   * @return
   */
  public static WxMenu fromJson(InputStream is) {
    return WxGsonBuilder.create().fromJson(new InputStreamReader(is, Charsets.UTF_8), WxMenu.class);
  }

  @Override
  public String toString() {
    return "WxMenu{" +
        "buttons=" + buttons +
        '}';
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

    @Override
    public String toString() {
      return "WxMenuButton{" +
          "type='" + type + '\'' +
          ", name='" + name + '\'' +
          ", key='" + key + '\'' +
          ", url='" + url + '\'' +
          ", subButtons=" + subButtons +
          '}';
    }
  }

}

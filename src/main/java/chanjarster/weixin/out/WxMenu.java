package chanjarster.weixin.out;

import java.util.ArrayList;
import java.util.List;

/**
 * 公众号菜单
 * @author chanjarster
 *
 */
public class WxMenu {

  protected List<WxMenuButton> button = new ArrayList<WxMenuButton>();

  public List<WxMenuButton> getButton() {
    return button;
  }

  public void setButton(List<WxMenuButton> button) {
    this.button = button;
  }
  
  public static class WxMenuButton {

    protected String type;
    protected String name;
    protected String key;
    protected String url;
    
    protected List<WxMenuButton> sub_button = new ArrayList<WxMenuButton>();

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

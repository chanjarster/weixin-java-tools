package me.chanjar.weixin.mp.bean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WxMpMaterial {

  private String name;
  private File file;
  private String videoTitle;
  private String videoIntroduction;

  public WxMpMaterial() {
  }

  public WxMpMaterial(String name, File file, String videoTitle, String videoIntroduction) {
    this.name = name;
    this.file = file;
    this.videoTitle = videoTitle;
    this.videoIntroduction = videoIntroduction;
  }

  public Map<String, String> getForm() {
    Map<String, String> form = new HashMap<String, String>();
    form.put("title", videoTitle);
    form.put("introduction", videoIntroduction);
    return form;
  }

  public String getVideoTitle() {
    return videoTitle;
  }

  public void setVideoTitle(String videoTitle) {
    this.videoTitle = videoTitle;
  }

  public String getVideoIntroduction() {
    return videoIntroduction;
  }

  public void setVideoIntroduction(String videoIntroduction) {
    this.videoIntroduction = videoIntroduction;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "WxMpMaterial [" + "name=" + name + ", file=" + file + ", videoTitle=" + videoTitle + ", videoIntroduction=" + videoIntroduction + "]";
  }
}

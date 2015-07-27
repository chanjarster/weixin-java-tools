package me.chanjar.weixin.mp.bean.result;

import java.io.Serializable;

public class WxMpMaterialCountResult implements Serializable {

  private int voiceCount;
  private int videoCount;
  private int imageCount;
  private int newsCount;

  public int getVoiceCount() {
    return voiceCount;
  }

  public void setVoiceCount(int voiceCount) {
    this.voiceCount = voiceCount;
  }

  public int getVideoCount() {
    return videoCount;
  }

  public void setVideoCount(int videoCount) {
    this.videoCount = videoCount;
  }

  public int getImageCount() {
    return imageCount;
  }

  public void setImageCount(int imageCount) {
    this.imageCount = imageCount;
  }

  public int getNewsCount() {
    return newsCount;
  }

  public void setNewsCount(int newsCount) {
    this.newsCount = newsCount;
  }

  @Override
  public String toString() {
    return "WxMpMaterialCountResult [" + "voiceCount=" + voiceCount + ", videoCount=" + videoCount
        + ", imageCount=" + imageCount + ", newsCount=" + newsCount + "]";
  }
}


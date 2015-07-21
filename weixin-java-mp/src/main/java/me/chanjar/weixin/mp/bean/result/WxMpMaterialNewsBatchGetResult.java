package me.chanjar.weixin.mp.bean.result;

import me.chanjar.weixin.mp.bean.WxMpMaterialNews;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WxMpMaterialNewsBatchGetResult implements Serializable {

  private int totalCount;
  private int itemCount;
  private List<WxMaterialNewsBatchGetNewsItem> items;

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public List<WxMaterialNewsBatchGetNewsItem> getItems() {
    return items;
  }

  public void setItems(List<WxMaterialNewsBatchGetNewsItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "WxMpMaterialNewsBatchGetResult [" + "totalCount=" + totalCount + ", itemCount=" + itemCount + ", items=" + items + "]";
  }

  public static class WxMaterialNewsBatchGetNewsItem {
    private String mediaId;
    private Date updateTime;
    private WxMpMaterialNews content;

    public String getMediaId() {
      return mediaId;
    }

    public void setMediaId(String mediaId) {
      this.mediaId = mediaId;
    }

    public Date getUpdateTime() {
      return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
    }

    public WxMpMaterialNews getContent() {
      return content;
    }

    public void setContent(WxMpMaterialNews content) {
      this.content = content;
    }

    @Override
    public String toString() {
      return "WxMaterialNewsBatchGetNewsItem [" + "mediaId=" + mediaId + ", updateTime=" + updateTime + ", content=" + content + "]";
    }
  }
}

package me.chanjar.weixin.mp.bean.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WxMpMaterialFileBatchGetResult implements Serializable {

  private int totalCount;
  private int itemCount;
  private List<WxMaterialFileBatchGetNewsItem> items;

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

  public List<WxMaterialFileBatchGetNewsItem> getItems() {
    return items;
  }

  public void setItems(List<WxMaterialFileBatchGetNewsItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "WxMpMaterialFileBatchGetResult [" + "totalCount=" + totalCount + ", itemCount=" + itemCount + ", items=" + items + "]";
  }

  public static class WxMaterialFileBatchGetNewsItem {
    private String mediaId;
    private Date updateTime;
    private String name;
    private String url;

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

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    @Override
    public String toString() {
      return "WxMaterialFileBatchGetNewsItem [" + "mediaId=" + mediaId + ", updateTime=" + updateTime + ", name=" + name + ", url=" + url + "]";
    }
  }
}

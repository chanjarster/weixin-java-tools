package me.chanjar.weixin.common.util.xml;

/**
 * Created by qianjia on 15/1/19.
 */
public class XStreamMediaIdConverter extends XStreamCDataConverter {
  @Override
  public String toString(Object obj) {
    return "<MediaId>" + super.toString(obj) + "</MediaId>";
  }
}

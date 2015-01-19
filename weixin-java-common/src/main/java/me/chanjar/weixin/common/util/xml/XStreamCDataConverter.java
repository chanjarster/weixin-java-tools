package me.chanjar.weixin.common.util.xml;

import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * Created by qianjia on 15/1/19.
 */
public class XStreamCDataConverter extends StringConverter {

  @Override
  public String toString(Object obj) {
    return "<![CDATA[" + super.toString(obj) + "]]>";
  }

}

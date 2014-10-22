package me.chanjar.weixin.common.util.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Daniel Qian
 */
public class MediaIdMarshaller extends XmlAdapter<String, String> {

  @Override
  public String marshal(String arg0) throws Exception {
    return "<MediaId><![CDATA[" + arg0 + "]]></MediaId>";
  }

  @Override
  public String unmarshal(String arg0) throws Exception {
    // do nothing
    return arg0;
  }

}

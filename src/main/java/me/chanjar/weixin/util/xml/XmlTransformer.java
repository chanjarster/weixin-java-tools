package me.chanjar.weixin.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.*;

import me.chanjar.weixin.bean.*;
import org.xml.sax.InputSource;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class XmlTransformer {

  protected static final JAXBContext jaxbContext = initJAXBContext();

  /**
   * xml -> pojo
   *
   * @param clazz
   * @param xml
   * @return
   * @throws JAXBException
   */
  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, String xml) throws JAXBException {
    JAXBContext context = jaxbContext;
    Unmarshaller um = context.createUnmarshaller();
    T object = (T) um.unmarshal(new StringReader(xml));
    return object;
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, InputStream is) throws JAXBException {
    JAXBContext context = jaxbContext;
    Unmarshaller um = context.createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    T object = (T) um.unmarshal(inputSource);
    return object;
  }

  /**
   * pojo -> xml
   *
   * @param clazz
   * @param object
   * @return
   * @throws JAXBException
   */
  public static <T> String toXml(Class<T> clazz, T object) throws JAXBException {
    StringWriter stringWriter = new StringWriter();
    toXml(clazz, object, stringWriter);
    return stringWriter.getBuffer().toString();
  }

  public static <T> void toXml(Class<T> clazz, T object, Writer writer) throws JAXBException {
    JAXBContext context = jaxbContext;
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    m.setProperty(CharacterEscapeHandler.class.getName(), characterUnescapeHandler);
    m.marshal(object, writer);
  }

  protected static CharacterEscapeHandler characterUnescapeHandler = new CharacterUnescapeHandler();

  protected static class CharacterUnescapeHandler implements CharacterEscapeHandler {
    public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
      writer.write(ac, i, j);
    }
  }

  private static JAXBContext initJAXBContext() {
    /*
     * JAXBContext对象是线程安全的，根据官方文档的建议将对象作为全局实例
     * https://jaxb.java.net/guide/Performance_and_thread_safety.html
     */
    try {
      return JAXBContext.newInstance(
          WxXmlOutMessage.class,
          WxXmlOutImageMessage.class,
          WxXmlOutMewsMessage.class,
          WxXmlOutMusicMessage.class,
          WxXmlOutTextMessage.class,
          WxXmlOutVideoMessage.class,
          WxXmlOutVoiceMessage.class,
          WxXmlMessage.class);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

}

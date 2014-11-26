package me.chanjar.weixin.mp.util.xml;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import me.chanjar.weixin.mp.bean.*;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XmlTransformer {

  protected static final JAXBContext JAXB_CONTEXT = initJAXBContext();

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
    Unmarshaller um = JAXB_CONTEXT.createUnmarshaller();
    T object = (T) um.unmarshal(new StringReader(xml));
    return object;
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, InputStream is) throws JAXBException {
    Unmarshaller um = JAXB_CONTEXT.createUnmarshaller();
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
    Marshaller m = JAXB_CONTEXT.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    m.setProperty(CharacterEscapeHandler.class.getName(), CHAR_ESCAPE_HANDLER);
    m.setProperty(Marshaller.JAXB_FRAGMENT, true);
    m.marshal(object, writer);
  }

  protected static final CharacterEscapeHandler CHAR_ESCAPE_HANDLER = new CharacterUnescapeHandler();

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
          WxMpXmlOutMessage.class,
          WxMpMpXmlOutImageMessage.class,
          WxMpXmlOutNewsMessage.class,
          WxMpXmlOutMusicMessage.class,
          WxMpXmlOutTextMessage.class,
          WxMpXmlOutVideoMessage.class,
          WxMpXmlOutVoiceMessage.class,
          WxMpXmlMessage.class);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

}

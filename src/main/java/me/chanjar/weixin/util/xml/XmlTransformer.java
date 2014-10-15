package me.chanjar.weixin.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class XmlTransformer {

  /**
   * xml -> pojo
   * @param clazz
   * @param object
   * @return
   * @throws JAXBException 
   */
  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, String xml) throws JAXBException {
    JAXBContext context = getJAXBContext(clazz);
    Unmarshaller um = context.createUnmarshaller();
    T object = (T) um.unmarshal(new StringReader(xml));
    return object;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, InputStream is) throws JAXBException {
    JAXBContext context = getJAXBContext(clazz);
    Unmarshaller um = context.createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    T object = (T) um.unmarshal(inputSource);
    return object;
  }
  
  /**
   * pojo -> xml
   * @param clazz
   * @return
   * @throws JAXBException 
   */
  public static <T> String toXml(Class<T> clazz, T object) throws JAXBException {
    StringWriter stringWriter = new StringWriter();
    toXml(clazz, object, stringWriter);
    return stringWriter.getBuffer().toString();
  }
  
  public static <T> void toXml(Class<T> clazz, T object, Writer writer) throws JAXBException {
    JAXBContext context = getJAXBContext(clazz);
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
  
  protected static Map<Class<?>, JAXBContext> jaxbContexts = new HashMap<Class<?>, JAXBContext>();
  
  protected static JAXBContext getJAXBContext(Class<?> clazz){
	//JAXBContext是线程安全的,且创建相对比较耗性能,使用map缓存,并发下多次创建没问题.
	JAXBContext context = jaxbContexts.get(clazz);
	if(context == null){
	  try {
	    context = JAXBContext.newInstance(clazz);
	  } catch (JAXBException e) {
	    throw new RuntimeException("创建JAXBContext实例失败:" + clazz.getName(), e);
	  }
	  jaxbContexts.put(clazz, context);
	}
	return context;
  }
  
}

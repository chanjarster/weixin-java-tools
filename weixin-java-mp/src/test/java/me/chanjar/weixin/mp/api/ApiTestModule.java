package me.chanjar.weixin.mp.api;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.xml.sax.InputSource;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxXmlMpInMemoryConfigStorage config = fromXml(WxXmlMpInMemoryConfigStorage.class, is1);
      WxMpServiceImpl wxService = new WxMpServiceImpl();
      wxService.setWxMpConfigStorage(config);

      binder.bind(WxMpServiceImpl.class).toInstance(wxService);
      binder.bind(WxMpConfigStorage.class).toInstance(config);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromXml(Class<T> clazz, InputStream is) throws JAXBException {
    Unmarshaller um = JAXBContext.newInstance(clazz).createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    T object = (T) um.unmarshal(inputSource);
    return object;
  }

  @XmlRootElement(name = "xml")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class WxXmlMpInMemoryConfigStorage extends WxMpInMemoryConfigStorage {
    
    protected String openId;

    public String getOpenId() {
      return openId;
    }
    public void setOpenId(String openId) {
      this.openId = openId;
    }
    @Override
    public String toString() {
      return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken
          + ", expiresIn=" + expiresIn + ", token=" + token + ", openId=" + openId + "]";
    }
     
  }
  
}

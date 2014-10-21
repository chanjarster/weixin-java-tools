package me.chanjar.weixin.mp.api;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import me.chanjar.weixin.mp.api.WxConfigStorage;
import me.chanjar.weixin.mp.api.WxInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxServiceImpl;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.xml.sax.InputSource;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxXmlConfigStorage config = fromXml(WxXmlConfigStorage.class, is1);
      WxServiceImpl wxService = new WxServiceImpl();
      wxService.setWxConfigStorage(config);

      binder.bind(WxServiceImpl.class).toInstance(wxService);
      binder.bind(WxConfigStorage.class).toInstance(config);
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
  public static class WxXmlConfigStorage extends WxInMemoryConfigStorage {
    
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

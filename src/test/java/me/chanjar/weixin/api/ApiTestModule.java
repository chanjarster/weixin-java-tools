package me.chanjar.weixin.api;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import me.chanjar.weixin.api.WxInMemoryConfigStorage;
import me.chanjar.weixin.api.WxServiceImpl;
import me.chanjar.weixin.util.xml.XmlTransformer;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxXmlConfigStorage config = XmlTransformer.fromXml(WxXmlConfigStorage.class, is1);
      WxServiceImpl wxService = new WxServiceImpl();
      wxService.setWxConfigStorage(config);

      binder.bind(WxServiceImpl.class).toInstance(wxService);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
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

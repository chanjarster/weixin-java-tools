package me.chanjar.weixin.mp.demo;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;

/**
 * @author Daniel Qian
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
class WxMpDemoInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken
        + ", expiresIn=" + expiresIn + ", token=" + token + ", aesKey=" + aesKey + "]";
  }


  public static WxMpDemoInMemoryConfigStorage fromXml(InputStream is) throws JAXBException {
    Unmarshaller um = JAXBContext.newInstance(WxMpDemoInMemoryConfigStorage.class).createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    return (WxMpDemoInMemoryConfigStorage) um.unmarshal(inputSource);
  }

}

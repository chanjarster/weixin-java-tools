package me.chanjar.weixin.demo;

import me.chanjar.weixin.api.WxInMemoryConfigStorage;
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
class WxTestConfigStorage extends WxInMemoryConfigStorage {

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken
        + ", expiresIn=" + expiresIn + ", token=" + token + ", aesKey=" + aesKey + "]";
  }


  public static WxTestConfigStorage fromXml(InputStream is) throws JAXBException {
    Unmarshaller um = JAXBContext.newInstance(WxTestConfigStorage.class).createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    return (WxTestConfigStorage) um.unmarshal(inputSource);
  }

}

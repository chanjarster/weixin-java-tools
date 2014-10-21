package me.chanjar.weixin.enterprise.demo;

import me.chanjar.weixin.enterprise.api.WxInMemoryCpConfigStorage;
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
class WxTestCpConfigStorage extends WxInMemoryCpConfigStorage {

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [corpId=" + corpId + ", corpSecret=" + corpSecret + ", accessToken=" + accessToken
        + ", expiresIn=" + expiresIn + ", token=" + token + ", aesKey=" + aesKey + "]";
  }


  public static WxTestCpConfigStorage fromXml(InputStream is) throws JAXBException {
    Unmarshaller um = JAXBContext.newInstance(WxTestCpConfigStorage.class).createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    return (WxTestCpConfigStorage) um.unmarshal(inputSource);
  }

}

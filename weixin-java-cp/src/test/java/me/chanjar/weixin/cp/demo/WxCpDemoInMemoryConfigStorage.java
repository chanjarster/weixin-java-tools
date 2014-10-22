package me.chanjar.weixin.cp.demo;

import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
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
class WxCpDemoInMemoryConfigStorage extends WxCpInMemoryConfigStorage {

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appidOrCorpid=" + corpId + ", corpSecret=" + corpSecret + ", accessToken=" + accessToken
        + ", expiresIn=" + expiresIn + ", token=" + token + ", aesKey=" + aesKey + "]";
  }


  public static WxCpDemoInMemoryConfigStorage fromXml(InputStream is) throws JAXBException {
    Unmarshaller um = JAXBContext.newInstance(WxCpDemoInMemoryConfigStorage.class).createUnmarshaller();
    InputSource inputSource = new InputSource(is);
    inputSource.setEncoding("utf-8");
    return (WxCpDemoInMemoryConfigStorage) um.unmarshal(inputSource);
  }

}

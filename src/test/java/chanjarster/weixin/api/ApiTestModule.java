package chanjarster.weixin.api;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import chanjarster.weixin.api.WxBaseAPITest.WxXmlConfigStorage;
import chanjarster.weixin.util.xml.XmlTransformer;

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

}

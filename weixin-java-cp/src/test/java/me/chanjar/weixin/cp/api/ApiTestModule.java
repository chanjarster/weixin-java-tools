package me.chanjar.weixin.cp.api;

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
      WxXmlCpInMemoryConfigStorage config = fromXml(WxXmlCpInMemoryConfigStorage.class, is1);
      WxCpServiceImpl wxService = new WxCpServiceImpl();
      wxService.setWxCpConfigStorage(config);

      binder.bind(WxCpServiceImpl.class).toInstance(wxService);
      binder.bind(WxCpConfigStorage.class).toInstance(config);
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
  public static class WxXmlCpInMemoryConfigStorage extends WxCpInMemoryConfigStorage {
    
    protected String userId;

    protected String departmentId;

    protected String tagId;

    public String getUserId() {
      return userId;
    }
    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getDepartmentId() {
      return departmentId;
    }

    public void setDepartmentId(String departmentId) {
      this.departmentId = departmentId;
    }

    public String getTagId() {
      return tagId;
    }

    public void setTagId(String tagId) {
      this.tagId = tagId;
    }

    @Override
    public String toString() {
      return super.toString() + " > WxXmlCpConfigStorage{" +
          "userId='" + userId + '\'' +
          ", departmentId='" + departmentId + '\'' +
          ", tagId='" + tagId + '\'' +
          '}';
    }
  }
  
}

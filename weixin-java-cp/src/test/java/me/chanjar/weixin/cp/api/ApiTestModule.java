package me.chanjar.weixin.cp.api;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

import java.io.InputStream;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
      WxXmlCpInMemoryConfigStorage config = fromXml(WxXmlCpInMemoryConfigStorage.class, is1);
      WxCpServiceImpl wxService = new WxCpServiceImpl();
      wxService.setWxCpConfigStorage(config);

      binder.bind(WxCpServiceImpl.class).toInstance(wxService);
      binder.bind(WxCpConfigStorage.class).toInstance(config);
  }

  public static <T> T fromXml(Class<T> clazz, InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.alias("xml", clazz);
    xstream.processAnnotations(clazz);
    return (T) xstream.fromXML(is);
  }

  @XStreamAlias("xml")
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

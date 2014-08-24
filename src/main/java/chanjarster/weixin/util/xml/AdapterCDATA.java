package chanjarster.weixin.util.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * http://stackoverflow.com/questions/14193944/jaxb-marshalling-unmarshalling-with-cdata
 * 
 * @author chanjarster
 *
 */
public class AdapterCDATA extends XmlAdapter<String, String> {

    @Override
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }
    
    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }
    
}

package sinosoft.module.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-07-17T10:34:07.198+08:00
 * Generated source version: 3.2.4
 *
 */
@WebServiceClient(name = "serverWS",
                  wsdlLocation = "http://lxgl.cccf.com.cn/services/serverWS?wsdl",
                  targetNamespace = "http://ws.module.sinosoft.com")
public class ServerWS extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ws.module.sinosoft.com", "serverWS");
    public final static QName ServerWSHttpport1 = new QName("http://ws.module.sinosoft.com", "serverWSHttpport1");
    public final static QName ServerWSSOAP11PortHttp = new QName("http://ws.module.sinosoft.com", "serverWSSOAP11port_http");
    public final static QName ServerWSSOAP12PortHttp = new QName("http://ws.module.sinosoft.com", "serverWSSOAP12port_http");
    static {
        URL url = null;
        try {
            url = new URL("http://lxgl.cccf.com.cn/services/serverWS?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ServerWS.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://lxgl.cccf.com.cn/services/serverWS?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ServerWS(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ServerWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServerWS() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ServerWS(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ServerWS(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ServerWS(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns ServerWSPortType
     */
    @WebEndpoint(name = "serverWSHttpport1")
    public ServerWSPortType getServerWSHttpport1() {
        return super.getPort(ServerWSHttpport1, ServerWSPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServerWSPortType
     */
    @WebEndpoint(name = "serverWSHttpport1")
    public ServerWSPortType getServerWSHttpport1(WebServiceFeature... features) {
        return super.getPort(ServerWSHttpport1, ServerWSPortType.class, features);
    }


    /**
     *
     * @return
     *     returns ServerWSPortType
     */
    @WebEndpoint(name = "serverWSSOAP11port_http")
    public ServerWSPortType getServerWSSOAP11PortHttp() {
        return super.getPort(ServerWSSOAP11PortHttp, ServerWSPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServerWSPortType
     */
    @WebEndpoint(name = "serverWSSOAP11port_http")
    public ServerWSPortType getServerWSSOAP11PortHttp(WebServiceFeature... features) {
        return super.getPort(ServerWSSOAP11PortHttp, ServerWSPortType.class, features);
    }


    /**
     *
     * @return
     *     returns ServerWSPortType
     */
    @WebEndpoint(name = "serverWSSOAP12port_http")
    public ServerWSPortType getServerWSSOAP12PortHttp() {
        return super.getPort(ServerWSSOAP12PortHttp, ServerWSPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServerWSPortType
     */
    @WebEndpoint(name = "serverWSSOAP12port_http")
    public ServerWSPortType getServerWSSOAP12PortHttp(WebServiceFeature... features) {
        return super.getPort(ServerWSSOAP12PortHttp, ServerWSPortType.class, features);
    }

}

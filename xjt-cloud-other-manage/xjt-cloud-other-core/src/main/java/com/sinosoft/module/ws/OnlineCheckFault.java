
package com.sinosoft.module.ws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-07-17T10:34:07.184+08:00
 * Generated source version: 3.2.4
 */

@WebFault(name = "onlineCheckFault", targetNamespace = "http://ws.module.sinosoft.com/xsd")
public class OnlineCheckFault extends Exception {

    private com.sinosoft.module.ws.xsd.OnlineCheckFault onlineCheckFault;

    public OnlineCheckFault() {
        super();
    }

    public OnlineCheckFault(String message) {
        super(message);
    }

    public OnlineCheckFault(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public OnlineCheckFault(String message, com.sinosoft.module.ws.xsd.OnlineCheckFault onlineCheckFault) {
        super(message);
        this.onlineCheckFault = onlineCheckFault;
    }

    public OnlineCheckFault(String message, com.sinosoft.module.ws.xsd.OnlineCheckFault onlineCheckFault, java.lang.Throwable cause) {
        super(message, cause);
        this.onlineCheckFault = onlineCheckFault;
    }

    public com.sinosoft.module.ws.xsd.OnlineCheckFault getFaultInfo() {
        return this.onlineCheckFault;
    }
}

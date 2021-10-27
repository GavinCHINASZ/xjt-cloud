package sinosoft.module.ws.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="onlineCheckFault" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "onlineCheckFault"
})
@XmlRootElement(name = "onlineCheckFault")
public class OnlineCheckFault {

    @XmlElement(required = true)
    protected Object onlineCheckFault;

    /**
     * ��ȡonlineCheckFault���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOnlineCheckFault() {
        return onlineCheckFault;
    }

    /**
     * ����onlineCheckFault���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOnlineCheckFault(Object value) {
        this.onlineCheckFault = value;
    }

}

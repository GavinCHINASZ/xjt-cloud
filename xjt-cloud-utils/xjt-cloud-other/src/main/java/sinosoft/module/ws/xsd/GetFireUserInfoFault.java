
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
 *         &lt;element name="getFireUserInfoFault" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
    "getFireUserInfoFault"
})
@XmlRootElement(name = "getFireUserInfoFault")
public class GetFireUserInfoFault {

    @XmlElement(required = true)
    protected Object getFireUserInfoFault;

    /**
     * ��ȡgetFireUserInfoFault���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGetFireUserInfoFault() {
        return getFireUserInfoFault;
    }

    /**
     * ����getFireUserInfoFault���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGetFireUserInfoFault(Object value) {
        this.getFireUserInfoFault = value;
    }

}

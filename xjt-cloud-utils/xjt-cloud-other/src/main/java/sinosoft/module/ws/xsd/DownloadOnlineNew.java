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
 *         &lt;element name="signMark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signStr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ruleContent" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    "signMark",
    "signStr",
    "ruleContent"
})
@XmlRootElement(name = "downloadOnline_new")
public class DownloadOnlineNew {

    @XmlElement(required = true, nillable = true)
    protected String signMark;
    @XmlElement(required = true, nillable = true)
    protected String signStr;
    @XmlElement(required = true, nillable = true)
    protected byte[] ruleContent;

    /**
     * ��ȡsignMark���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignMark() {
        return signMark;
    }

    /**
     * ����signMark���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignMark(String value) {
        this.signMark = value;
    }

    /**
     * ��ȡsignStr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignStr() {
        return signStr;
    }

    /**
     * ����signStr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignStr(String value) {
        this.signStr = value;
    }

    /**
     * ��ȡruleContent���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRuleContent() {
        return ruleContent;
    }

    /**
     * ����ruleContent���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRuleContent(byte[] value) {
        this.ruleContent = value;
    }

}

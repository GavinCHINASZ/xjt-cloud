
package com.sinosoft.module.ws.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="signMark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signStr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="xmlList" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    "xmlList"
})
@XmlRootElement(name = "getList_new")
public class GetListNew {

    @XmlElement(required = true, nillable = true)
    protected String signMark;
    @XmlElement(required = true, nillable = true)
    protected String signStr;
    @XmlElement(required = true, nillable = true)
    protected byte[] xmlList;

    /**
     * 获取signMark属性的值。
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
     * 设置signMark属性的值。
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
     * 获取signStr属性的值。
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
     * 设置signStr属性的值。
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
     * 获取xmlList属性的值。
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXmlList() {
        return xmlList;
    }

    /**
     * 设置xmlList属性的值。
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXmlList(byte[] value) {
        this.xmlList = value;
    }

}

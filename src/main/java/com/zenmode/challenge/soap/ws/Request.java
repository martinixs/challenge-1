//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.18 at 04:11:01 PM CEST 
//


package com.zenmode.challenge.soap.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="correctionId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="payload" type="{urn:mFlow}person"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "request")
public class Request {

    @XmlElement(required = true)
    protected String correctionId;
    @XmlElement(name = "payload", required = true)
    protected Person person;

    /**
     * Gets the value of the correctionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrectionId() {
        return correctionId;
    }

    /**
     * Sets the value of the correctionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrectionId(String value) {
        this.correctionId = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setPerson(Person value) {
        this.person = value;
    }

}

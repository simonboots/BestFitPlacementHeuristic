//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.06 at 12:20:00 PM CET 
//


package scp.common.xml.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sort">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shapeid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rotated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sort", propOrder = {
    "shapeid",
    "rotated"
})
public class Sort {

    protected int shapeid;
    protected boolean rotated;
    @XmlAttribute(required = true)
    protected int id;

    /**
     * Gets the value of the shapeid property.
     * 
     */
    public int getShapeid() {
        return shapeid;
    }

    /**
     * Sets the value of the shapeid property.
     * 
     */
    public void setShapeid(int value) {
        this.shapeid = value;
    }

    /**
     * Gets the value of the rotated property.
     * 
     */
    public boolean isRotated() {
        return rotated;
    }

    /**
     * Sets the value of the rotated property.
     * 
     */
    public void setRotated(boolean value) {
        this.rotated = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}

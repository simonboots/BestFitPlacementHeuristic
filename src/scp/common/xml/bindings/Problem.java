//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.23 at 02:24:44 PM CEST 
//


package scp.common.xml.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for problem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="problem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shapes" type="{http://www.stiefels.net/StockCuttingProblem}shapes"/>
 *         &lt;element name="solution" type="{http://www.stiefels.net/StockCuttingProblem}solution" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "problem", propOrder = {
    "shapes",
    "solution"
})
public class Problem {

    @XmlElement(required = true)
    protected Shapes shapes;
    protected Solution solution;

    /**
     * Gets the value of the shapes property.
     * 
     * @return
     *     possible object is
     *     {@link Shapes }
     *     
     */
    public Shapes getShapes() {
        return shapes;
    }

    /**
     * Sets the value of the shapes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Shapes }
     *     
     */
    public void setShapes(Shapes value) {
        this.shapes = value;
    }

    /**
     * Gets the value of the solution property.
     * 
     * @return
     *     possible object is
     *     {@link Solution }
     *     
     */
    public Solution getSolution() {
        return solution;
    }

    /**
     * Sets the value of the solution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Solution }
     *     
     */
    public void setSolution(Solution value) {
        this.solution = value;
    }

}
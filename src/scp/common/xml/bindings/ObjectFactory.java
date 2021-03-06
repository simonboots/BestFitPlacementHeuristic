/**
 *     Copyright (C) 2008 Benjamin Clauss, Simon Stiefel 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.21 at 10:15:49 PM CET 
//


package scp.common.xml.bindings;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the scp.common.xml.bindings package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Problem_QNAME = new QName("http://www.stiefels.net/StockCuttingProblem", "problem");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: scp.common.xml.bindings
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Coordinates }
     * 
     */
    public Coordinates createCoordinates() {
        return new Coordinates();
    }

    /**
     * Create an instance of {@link Placement }
     * 
     */
    public Placement createPlacement() {
        return new Placement();
    }

    /**
     * Create an instance of {@link Solution }
     * 
     */
    public Solution createSolution() {
        return new Solution();
    }

    /**
     * Create an instance of {@link Gap }
     * 
     */
    public Gap createGap() {
        return new Gap();
    }

    /**
     * Create an instance of {@link Optimizations }
     * 
     */
    public Optimizations createOptimizations() {
        return new Optimizations();
    }

    /**
     * Create an instance of {@link Sorting }
     * 
     */
    public Sorting createSorting() {
        return new Sorting();
    }

    /**
     * Create an instance of {@link Problem }
     * 
     */
    public Problem createProblem() {
        return new Problem();
    }

    /**
     * Create an instance of {@link Shape }
     * 
     */
    public Shape createShape() {
        return new Shape();
    }

    /**
     * Create an instance of {@link Placements }
     * 
     */
    public Placements createPlacements() {
        return new Placements();
    }

    /**
     * Create an instance of {@link Shapes }
     * 
     */
    public Shapes createShapes() {
        return new Shapes();
    }

    /**
     * Create an instance of {@link Optimization }
     * 
     */
    public Optimization createOptimization() {
        return new Optimization();
    }

    /**
     * Create an instance of {@link Sort }
     * 
     */
    public Sort createSort() {
        return new Sort();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Problem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.stiefels.net/StockCuttingProblem", name = "problem")
    public JAXBElement<Problem> createProblem(Problem value) {
        return new JAXBElement<Problem>(_Problem_QNAME, Problem.class, null, value);
    }

}

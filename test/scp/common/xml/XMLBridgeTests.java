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


package scp.common.xml;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import scp.common.*;

public class XMLBridgeTests {

	XMLBridge bridge = null;
	
	@Before
	public void setUp() throws Exception {
		bridge = new XMLBridge();
		bridge.loadFile(new File("test/scp/common/xml/TestFile.xml"));
	}

	@Test
	public void shapeListTest() {
		ArrayList<Shape> shapelist = null;
		
		try {
			 shapelist = (ArrayList<Shape>) bridge.getShapeList();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		assertEquals(3, shapelist.size());
	}
	
	@Test
	public void sortedShapeListTest() {
		ArrayList<Shape> shapelist = null;
		
		try {
			shapelist = (ArrayList<Shape>) bridge.getSortedShapeList();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, shapelist.get(2).isRotated());
	}

}

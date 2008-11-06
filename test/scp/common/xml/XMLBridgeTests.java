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

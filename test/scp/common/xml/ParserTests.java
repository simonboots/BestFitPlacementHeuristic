package scp.common.xml;


import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;

public class ParserTests implements IShapeCallback {
	
	Parser p = null;

	@Before
	public void setUp() throws Exception {
		p = new Parser("test/scp/common/xml/TestFile.xml");
		p.setSchemaFilename("StockCuttingProblem.xsd");
		p.setShapeCallback(this);
	}

	@Test
	public void shapeParser() {
		try {
			p.parse();
		} catch (FileNotFoundException e) {
			assertTrue(true);
			e.printStackTrace();
		} catch (XMLStreamException e) {
			assertTrue(true);
			e.printStackTrace();
		}
		
	}

	public void newShape(Integer id, Integer width, Integer height) {
		assertTrue((id == 1) && (width == 100) && (height == 50));
	}

}

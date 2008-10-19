package scp.common.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;

public class Parser {

	String documentFilename = null;
	String schemaFilename = null;
	IShapeCallback shapeDelegate = null;

	public Parser(String filename) {
		this.documentFilename = filename;
	}

	public void setSchemaFilename(String filename) {
		this.schemaFilename = filename;
	}

	public void setShapeDelegate(IShapeCallback delegate) {
		this.shapeDelegate = delegate;
	}

	public int parse() throws FileNotFoundException, XMLStreamException {
		if (!validate()) {
			return 0;
		}

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader reader = inputFactory.createXMLEventReader(new FileReader(documentFilename));

		ShapeParser shapeParser = null;
		if (shapeDelegate != null)
			shapeParser = new ShapeParser(shapeDelegate);

		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (shapeParser != null) shapeParser.event(event);
		}

		return 0;
	}

	private boolean validate() {
		// Fail if schema file is not available
		if (schemaFilename == null)
			return false;

		DocumentValidator validator = new DocumentValidator(new File(
				schemaFilename));
		if (validator.validateXMLDocument(new File(documentFilename))) {
			return true;
		}

		return true;
	}
	
	public static void main(String[] args) {
		Parser p = new Parser("src/TestFile.xml");
		p.setSchemaFilename("src/StockCuttingProblem.xsd");
		p.setShapeDelegate(new ShapeDelegateDemo());
		
		try {
			p.parse();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

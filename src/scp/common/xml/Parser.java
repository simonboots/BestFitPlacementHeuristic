package scp.common.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;

import scp.common.xml.states.BeginState;
import scp.common.xml.states.IParserState;

public class Parser {

	String documentFilename = null;
	String schemaFilename = null;
	IShapeCallback shapeCallback = null;
	IParserState state = null;
	ShapeDataCollector shapeDataCollector = null;

	public Parser(String filename) {
		this.documentFilename = filename;
		this.state = new BeginState();
		this.shapeCallback = new ShapeNullCallback();
	}

	public ShapeDataCollector getShapeDataCollector() {
		return shapeDataCollector;
	}

	public void setShapeDataCollector(ShapeDataCollector shapeDataCollector) {
		this.shapeDataCollector = shapeDataCollector;
	}

	public void setSchemaFilename(String filename) {
		this.schemaFilename = filename;
	}

	public IShapeCallback getShapeCallback() {
		return shapeCallback;
	}

	public void setShapeCallback(IShapeCallback callback) {
		this.shapeCallback = callback;
	}
	
	public void setParserState(IParserState state) {
		this.state = state;
	}
	
	public int parse() throws FileNotFoundException, XMLStreamException {
		if (!validate()) {
			return 0;
		}

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader reader = inputFactory.createXMLEventReader(new FileReader(documentFilename));

		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				state.startElement(this, (StartElement)event);
			} else if (event.isEndElement()) {
				state.endElement(this, (EndElement)event);
			} else if (event.isCharacters()) {
				state.characters(this, (Characters)event);
			}
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
}

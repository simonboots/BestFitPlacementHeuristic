package scp.common.xml.states;

import java.util.Iterator;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import scp.common.xml.Parser;

public class ShapeWidthState implements IParserState {

	public void characters(Parser parser, Characters event) {
		parser.getShapeDataCollector().setWidth(new Integer(event.getData()));
	}

	public void endElement(Parser parser, EndElement event) {
		if (event.getName().toString().equals("width")) {
			parser.setParserState(new ShapeState());
		}
	}

	public void setAttributes(Parser parser,
			Iterator<Attribute> attributeIterator) {
		// nothing to do
	}

	public void startElement(Parser parser, StartElement event) {
		// nothing to do
	}

}

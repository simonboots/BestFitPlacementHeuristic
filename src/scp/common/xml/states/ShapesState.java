package scp.common.xml.states;

import java.util.Iterator;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import scp.common.xml.Parser;
import scp.common.xml.ShapeDataCollector;

public class ShapesState implements IParserState {

	public void characters(Parser parser, Characters event) {
		// nothing to do
	}

	public void endElement(Parser parser, EndElement event) {
		if (event.getName().toString().equals("shapes")) {
			parser.setParserState(new ProblemState());
		}
	}

	public void startElement(Parser parser, StartElement event) {
		if (event.getName().toString().equals("shape")) {
			// create new ShapeDataCollector
			parser.setShapeDataCollector(new ShapeDataCollector());
			
			IParserState shapeState = new ShapeState();
			shapeState.setAttributes(parser, event.getAttributes());
			parser.setParserState(shapeState);
		}
	}

	public void setAttributes(Parser parser, Iterator<Attribute> attributeIterator) {
		// nothing to do		
	}
}

package scp.common.xml.states;

import java.util.Iterator;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import scp.common.xml.Parser;

public class ProblemState implements IParserState {

	public void characters(Parser parser, Characters event) {
		// nothing to do
	}

	public void endElement(Parser parser, EndElement event) {
		if (event.getName().toString().equals("problem")) 
		parser.setParserState(new BeginState());
	}

	public void startElement(Parser parser, StartElement event) {
		if (event.getName().toString().equals("shapes")) {
			parser.setParserState(new ShapesState());
		}

	}

	public void setAttributes(Parser parser,
			Iterator<Attribute> attributeIterator) {
		// nothing to do		
	}
}

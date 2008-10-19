package scp.common.xml.states;

import java.util.Iterator;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import scp.common.xml.Parser;

public class BeginState implements IParserState {

	public void characters(Parser parser, Characters event) {
		// TODO Auto-generated method stub

	}

	public void endElement(Parser parser, EndElement event) {
		// TODO Auto-generated method stub

	}

	public void startElement(Parser parser, StartElement event) {
		if (event.getName().toString().equals("{http://www.stiefels.net/StockCuttingProblem}problem")) {
			parser.setParserState(new ProblemState());
		}

	}

	public void setAttributes(Parser parser, Iterator<Attribute> attributeIterator) {
		// TODO Auto-generated method stub
		
	}
}

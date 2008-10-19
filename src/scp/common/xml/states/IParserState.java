package scp.common.xml.states;

import java.util.Iterator;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import scp.common.xml.Parser;

public interface IParserState {
	public void startElement(Parser parser, StartElement event);
	public void endElement(Parser parser, EndElement event);
	public void characters(Parser parser, Characters event);
	public void setAttributes(Parser parser, Iterator<Attribute> attributeIterator);
}

package scp.common.xml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public abstract class WriterJumper {
	
	XMLEventReader reader = null;
	XMLEventWriter writer = null;
	
	public void setEventReader(XMLEventReader reader) {
		this.reader = reader;
	}
	
	public void setEventWriter(XMLEventWriter writer) {
		this.writer = writer;
	}
	
	public abstract void jump() throws XMLStreamException;
	
	protected void flushEvent(XMLEvent event) throws XMLStreamException {
		writer.add(event);
	}
}

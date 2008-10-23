package scp.common.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class Writer {
	
	public static final String SHAPES = "shapes";
	
	String documentFilename = null;
	XMLEventReader reader = null;
	XMLEventWriter writer = null;
	
	public Writer(String filename) {
		this.documentFilename = filename;
	}
	
	public void open() throws XMLStreamException, FactoryConfigurationError, IOException  {		
		reader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader(documentFilename));
		writer = XMLOutputFactory.newInstance().createXMLEventWriter(new FileWriter(documentFilename));
	}
	
	public void jump(WriterJumper jumper) throws XMLStreamException {
		jumper.setEventReader(reader);
		jumper.setEventWriter(writer);
		jumper.jump();
	}
	
	public void addEvent(XMLEvent event) throws XMLStreamException {
		writer.add(event);
	}
	
	public void finish() throws XMLStreamException {
		while (reader.hasNext()) {
			writer.add(reader.nextEvent());
		}
		
		writer.flush();
	}
	
	public static void main(String args[]) throws IOException {
		Writer w = new Writer("src/TestFile.xml");
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();

		try {
			XMLEvent e1 = eventFactory.createStartElement("", "nix", "localName", null, null);
			XMLEvent e2 = eventFactory.createEndElement("", null, "localName");
			w.open();
			w.jump(new AfterShapesJumper());
			w.addEvent(e1);
			w.addEvent(e2);
			w.finish();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

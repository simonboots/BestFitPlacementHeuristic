package scp.common.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

public class AfterShapesJumper extends WriterJumper {

	public void jump() throws XMLStreamException {
		while (reader.hasNext()) {
			XMLEvent event = (XMLEvent) reader.next();
			super.flushEvent(event);
			
			if (event.isEndElement()) {
				if (((EndElement)event).getName().toString().equals(Writer.SHAPES)) {
					return;
				}
			}
		}
	}
}

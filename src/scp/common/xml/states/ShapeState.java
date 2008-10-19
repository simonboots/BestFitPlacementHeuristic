package scp.common.xml.states;

import java.util.Iterator;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import scp.common.xml.IShapeCallback;
import scp.common.xml.Parser;
import scp.common.xml.ShapeDataCollector;

public class ShapeState implements IParserState {

	public void characters(Parser parser, Characters event) {
		// nothing to do
	}

	public void endElement(Parser parser, EndElement event) {
		if (event.getName().toString().equals("shape")) {
			// Call callback
			IShapeCallback callback = parser.getShapeCallback();
			ShapeDataCollector collector = parser.getShapeDataCollector();
			callback.newShape(collector.getShapeid(), collector.getWidth(),
					collector.getHeight());

			parser.setParserState(new ShapesState());
		}
	}

	public void startElement(Parser parser, StartElement event) {
		if (event.getName().toString().equals("height")) {
			parser.setParserState(new ShapeHeightState());
		} else if (event.getName().toString().equals("width")) {
			parser.setParserState(new ShapeWidthState());
		}
	}

	public void setAttributes(Parser parser,
			Iterator<Attribute> attributeIterator) {
		while (attributeIterator.hasNext()) {
			Attribute attribute = attributeIterator.next();

			// find id attribute
			if (attribute.getName().toString().equals("id")) {
				parser.getShapeDataCollector().setShapeid(
						new Integer(attribute.getValue()));
			}
		}
	}
}

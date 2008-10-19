package scp.common.xml;

import java.util.Iterator;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ShapeParser {
	
	private IShapeCallback shapeDelegate = null;
	private Vector<QName> stack = new Vector<QName>();
	private String characters = null;
	private int shapeid = 0;
	private int height = 0;
	private int width = 0;
	
	public ShapeParser(IShapeCallback delegate) {
		this.shapeDelegate = delegate;
	}

	public void event(XMLEvent event) {
	
		// Start element
		if (event.isStartElement()) {
			startElement((StartElement)event);
		}
		
		// End element
		if (event.isEndElement()) {
			try {
				endElement((EndElement)event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Characters
		if (event.isCharacters()) {
			characters = ((Characters)event).getData();
		}
	}
	
	public void startElement(StartElement element) {
		stack.add(element.getName());
		String name = element.getName().toString();
		
		// shape
		if (name.toString().equals("shape")) {
			// get all attribute
			Iterator iterator = element.getAttributes();
			
	        while (iterator.hasNext()) {
	          Attribute attribute = (Attribute) iterator.next();
	          
	          // find id attribute
	          if (attribute.getName().toString().equals("id")) {
	        	  shapeid = new Integer(attribute.getValue());
	          }
	        }
		}
	}
	
	public void endElement(EndElement element) throws Exception {
		String name = element.getName().toString();
		
		if (name.equals("height")) {
			height = new Integer(characters);
		}
		
		if (name.equals("width")) {
			width = new Integer(characters);
		}
		
		if (name.equals("shape")) {
			shapeDelegate.newShape(shapeid, width, height);
		}
		
		if (stack.lastElement().equals(element.getName())) {
			stack.remove(stack.lastElement());
		} else {
			throw new Exception("Stack mismatch!");
		}
	}

}

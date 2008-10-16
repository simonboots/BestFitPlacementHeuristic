package scp.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class Parser {

    public static void main(String[] args) {
        // Validate XML Document
        DocumentValidator validator = new DocumentValidator(new File("StockCuttingProblem.xsd"));
        if (validator.validateXMLDocument(new File("TestFile.xml"))) {
            System.out.println("File is valid!");
        } else {
            System.out.println("File is NOT valid!");
        }

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        System.out.println("Factory: " + inputFactory);

        String filename = "TestFile.xml";

        XMLEventReader reader = null;

        try {
            reader = inputFactory.createXMLEventReader(filename, new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (XMLStreamException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (reader.hasNext()) {
            XMLEvent event = null;
            try {
                event = reader.nextEvent();
            } catch (XMLStreamException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.print(event.toString());
        }
    }
}

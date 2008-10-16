package scp.common.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DocumentValidator {

    private File schemaFile;

    public DocumentValidator(File schemaFile) {
        super();
        this.schemaFile = schemaFile;
    }

    public boolean validateXMLDocument(File xmlDocument) {
        boolean isValid = true;
        DocumentBuilder parser = null;
        Document document = null;
        Schema schema = null;

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            schema = factory.newSchema(this.schemaFile);
            Validator validator = schema.newValidator();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            parser = builderFactory.newDocumentBuilder();
            document = parser.parse(xmlDocument);
            validator.validate(new DOMSource(document));

        } catch (SAXException e) {
            // TODO Auto-generated catch block
            isValid = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            isValid = false;
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            isValid = false;
        }

        return isValid;
    }
}

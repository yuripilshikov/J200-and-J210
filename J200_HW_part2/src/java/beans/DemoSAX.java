package beans;

import entity.Client;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import xml.sax.ClientParser;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class DemoSAX implements DemoSAXLocal {

    @Override
    public void getClientsFromXMLSAX() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File("d:\\test\\Clients.xml"), new ClientParser());
        } catch (SAXException ex) {
            Logger.getLogger(DemoSAX.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemoSAX.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DemoSAX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

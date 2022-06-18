package xml.sax;

import entity.Client;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author YuriPilshikov
 */
public class DemoSAX {

    public static List<Client> getClientsFromXMLSAX(String filter) {
        List<Client> clients;
        List<Client> filteredClients = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ClientParser cp = new ClientParser();
            parser.parse(new File("Clients.xml"), cp);
            clients = cp.getClients();
            
            // filter here
            for(Client c : clients) {
                if(c.getModel().toLowerCase().contains(filter)) {
                    filteredClients.add(c);
                }
            }
            
        } catch (SAXException ex) {
            Logger.getLogger(DemoSAX.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemoSAX.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DemoSAX.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return filteredClients;
    }
}

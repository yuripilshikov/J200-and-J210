package xml.sax;

import entity.Client;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author YuriPilshikov
 */
public class ClientParser extends DefaultHandler {
    private List<Client> clients = new ArrayList<>();

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("client")) {
            //int id = Integer.parseInt(attributes.getValue("id"));
            String type = attributes.getValue("type");
            String model = attributes.getValue("model");
            String ip = attributes.getValue("ip");
            
            Client c = new Client();
            //c.setIdclient(id);
            c.setType(type);
            c.setModel(model);
            c.setIp(ip);
            clients.add(c);
        }
    }   

    @Override
    public void endDocument() throws SAXException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> SAX PARSER RESULTS");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        for(Client c : clients) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + c.getIp() + " " + c.getModel());
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    
    
    
}

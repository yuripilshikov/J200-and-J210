package xml;

import entity.Address;
import entity.Client;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author YuriPilshikov
 */
public class Transformer {
    
    public static final File xmlFile = new File("Clients.xml");
    static {
        if(!xmlFile.exists()) try {
            xmlFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createXML(List<Client> clients) {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<clients>\n");
        for (Client client : clients) {
            String entry = "<client id=\"" + client.getIdclient()
                    + "\" type=\"" + client.getType()
                    + "\" model=\"" + client.getModel()
                    + "\" ip=\"" + client.getIp() + "\"/>\n";
            sb.append(entry);
        }
        sb.append("</clients>");
        
        try(FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }    
    
    public static void createXMLDOM(List<Client> clients) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();
            Element root = document.createElement("clients");
            document.appendChild(root);
            for(Client c : clients) {
                Element clientElement = document.createElement("client");
                clientElement.setAttribute("id", String.valueOf(c.getIdclient()));
                clientElement.setAttribute("type", c.getType());
                clientElement.setAttribute("model", c.getModel());
                List<Address> addressList = c.getAddressList();
                if(addressList != null && addressList.size() > 0) {
                    Element addrElement = document.createElement("address");
                    for(Address a : addressList) {
                        addrElement.setAttribute("id", String.valueOf(a.getIdaddress()));
                        addrElement.setAttribute("city", a.getCity());
                        addrElement.setAttribute("street", a.getStreet());
                        addrElement.setAttribute("num", String.valueOf(a.getNum()));
                        ///
                        clientElement.appendChild(addrElement);
                    }
                }
                root.appendChild(clientElement);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            javax.xml.transform.Transformer t = tf.newTransformer();
            DOMSource doms = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            t.transform(doms, result);           
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

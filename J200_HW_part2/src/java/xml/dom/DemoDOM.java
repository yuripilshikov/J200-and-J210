package xml.dom;

import entity.Address;
import entity.Client;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class DemoDOM {

    public static List<Client> getClientsFromXMLDOM(String filterStr) {
        List<Client> clients = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("Clients.xml"));

            NodeList clientsElements = document.getDocumentElement().getElementsByTagName("client");

            for (int i = 0; i < clientsElements.getLength(); i++) {
                Node client = clientsElements.item(i);
                NamedNodeMap attributes = client.getAttributes();

                String model = attributes.getNamedItem("model").getNodeValue();
                if (model.toLowerCase().contains(filterStr.toLowerCase())) {
                    Client c = new Client();
                    c.setType(attributes.getNamedItem("type").getNodeValue());
                    c.setModel(model);
                    c.setIp(attributes.getNamedItem("ip").getNodeValue());
                    
                    NodeList addrList = client.getChildNodes();
                    for(int j = 0; j < addrList.getLength(); j++) {
                        Node childNode = addrList.item(j);
                        if(childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element tempAddrElem = (Element)childNode;
                            Address address = new Address();
                            address.setIdaddress(Integer.parseInt(tempAddrElem.getAttribute("id")));
                            address.setCity(tempAddrElem.getAttribute("city"));
                            address.setStreet(tempAddrElem.getAttribute("street"));
                            
                            try{
                                address.setNum(Integer.parseInt(tempAddrElem.getAttribute("num")));
                            } catch(NumberFormatException e) {
                                address.setNum(-1);
                            }
                            
                            try {
                                address.setFlat(Integer.parseInt(tempAddrElem.getAttribute("flat")));
                            } catch(NumberFormatException e) {
                                address.setFlat(-1);
                            }
                            
                            try {
                                address.setSubnum(Integer.parseInt(tempAddrElem.getAttribute("subnum")));                                
                            } catch(NumberFormatException e) {
                                address.setSubnum(-1);
                            }
                            
                            address.setExtra(tempAddrElem.getAttribute("extra"));
                            c.addAddress(address);
                        }
                    }
                    
                    clients.add(c);
                }
            }

//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");            
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> DOM PARSER RESULTS");
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            for(Client c : clients) {
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + c.getIp() + " " + c.getModel());
//            }
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DemoDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DemoDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemoDOM.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clients;
    }
}

package restclient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import restclient.objects.Address;
import restclient.objects.MyClient;

/**
 *
 * @author YuriPilshikov
 */
public class RESTclient {

    public static void main(String[] args) {
        MyClientClient clientClient = new MyClientClient();
        MyAddressClient addressClient = new MyAddressClient();
        
        List<MyClient> clients = new ArrayList<>();
        String xml = clientClient.findAll(String.class);
        System.out.println(xml);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
//            File file = new File("tempXML.xml");
//            file.createNewFile();
//            FileWriter fw = new FileWriter(file);
//            fw.write(xml);
//            fw.close();
            
            // СЮДА МОЖНО ПЕРЕДАТЬ URI
            Document document = builder.parse("http://localhost:26213/J200_HW_part2/webresources/client");
            
            //document.getDocumentElement().normalize();
            System.out.println("Root element: " + document.getDocumentElement().getNodeName());
            //NodeList clientsElements = document.getDocumentElement().getElementsByTagName("client");
            System.out.println(document.getDocumentElement().getChildNodes().toString());
            
            NodeList clientsElements = document.getElementsByTagName("client");
            

            for (int i = 0; i < clientsElements.getLength(); i++) {
                Node myClientNode = clientsElements.item(i);
                //NamedNodeMap attributes = myClientNode.getAttributes();
                NodeList childNodeList = myClientNode.getChildNodes();
                int id;
                String model, ip, type;
                for(int j = 0; j < childNodeList.getLength(); j++) {
                    System.out.println(childNodeList.item(0).getNodeName());
                    
                }
               

                MyClient c = new MyClient();
//                System.out.println(attributes.getNamedItem("type"));
//                System.out.println(attributes.getNamedItem("model"));
//                System.out.println(attributes.getNamedItem("ip"));
                //c.setType(attributes.getNamedItem("type").getNodeValue());
                //c.setModel(attributes.getNamedItem("model").getNodeValue());
                //c.setIp(attributes.getNamedItem("ip").getNodeValue());

                NodeList addrList = myClientNode.getChildNodes();
                for (int j = 0; j < addrList.getLength(); j++) {
                    Node childNode = addrList.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element tempAddrElem = (Element) childNode;
                        Address address = new Address();
                        
//                        System.out.println(tempAddrElem.getAttribute("city"));
//                        address.setId(Integer.parseInt(tempAddrElem.getAttribute("id")));
//                        address.setCity(tempAddrElem.getAttribute("city"));
//                        address.setStreet(tempAddrElem.getAttribute("street"));
//
//                        try {
//                            address.setNum(Integer.parseInt(tempAddrElem.getAttribute("num")));
//                        } catch (NumberFormatException e) {
//                            address.setNum(-1);
//                        }
//
//                        try {
//                            address.setFlat(Integer.parseInt(tempAddrElem.getAttribute("flat")));
//                        } catch (NumberFormatException e) {
//                            address.setFlat(-1);
//                        }
//
//                        try {
//                            address.setSubnum(Integer.parseInt(tempAddrElem.getAttribute("subnum")));
//                        } catch (NumberFormatException e) {
//                            address.setSubnum(-1);
//                        }
//
//                        address.setExtra(tempAddrElem.getAttribute("extra"));
//                        c.addAddress(address);
                    }
                }

                clients.add(c);

            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RESTclient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(RESTclient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTclient.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (MyClient c : clients) {
//            System.out.println(c);
        }

    }

}

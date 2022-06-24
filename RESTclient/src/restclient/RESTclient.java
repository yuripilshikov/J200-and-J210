package restclient;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import restclient.objects.Address;
import restclient.objects.MyClient;

/**
 *
 * @author YuriPilshikov
 */
public class RESTclient {

    MyClientClient clientClient = new MyClientClient();
    MyAddressClient addressClient = new MyAddressClient();
    //List<MyClient> clients = new ArrayList<>();

    public List<MyClient> readXML() {
        String xml = clientClient.findAll(String.class);
        return xmlParseClient(xml);
    }

    public MyClientClient getClientClient() {
        return clientClient;
    }

    public MyAddressClient getAddressClient() {
        return addressClient;
    }

    MyClient findClient(int number) {
        String xml = clientClient.find(String.class, String.valueOf(number));
        List<MyClient> clients = xmlParseClient(xml);
        return clients.get(0);

    }

    Address findAddress(int number) {
        String xml = addressClient.find(String.class, String.valueOf(number));

        // It is a good idea to move it to another method... but I don't have enough time...
        Address address = new Address();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            NodeList clientsElements = document.getElementsByTagName("address");
            for (int i = 0; i < clientsElements.getLength(); i++) {
                Node addressNode = clientsElements.item(i);
                NodeList addressSubNodes = addressNode.getChildNodes();

                    for (int k = 0; k < addressSubNodes.getLength(); k++) {
                        if (addressSubNodes.item(k).getNodeName().equals("city")) {
                            address.setCity(addressSubNodes.item(k).getTextContent());
                        } else if (addressSubNodes.item(k).getNodeName().equals("extra")) {
                            address.setExtra(addressSubNodes.item(k).getTextContent());
                        } else if (addressSubNodes.item(k).getNodeName().equals("flat")) {
                            address.setFlat(Integer.parseInt(addressSubNodes.item(k).getTextContent()));
                        } else if (addressSubNodes.item(k).getNodeName().equals("idaddress")) {
                            address.setId(Integer.parseInt(addressSubNodes.item(k).getTextContent()));
                        } else if (addressSubNodes.item(k).getNodeName().equals("num")) {
                            address.setNum(Integer.parseInt(addressSubNodes.item(k).getTextContent()));
                        } else if (addressSubNodes.item(k).getNodeName().equals("street")) {
                            address.setStreet(addressSubNodes.item(k).getTextContent());
                        } else if (addressSubNodes.item(k).getNodeName().equals("subnum")) {
                            address.setSubnum(Integer.parseInt(addressSubNodes.item(k).getTextContent()));
                        }
                    }

                
            }

        } catch (SAXException ex) {
            Logger.getLogger(RESTclient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTclient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RESTclient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return address;
    }

    List<MyClient> xmlParseClient(String xml
    ) {
        List<MyClient> clients = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // СЮДА МОЖНО ПЕРЕДАТЬ URI
            //Document document = builder.parse("http://localhost:26213/J200_HW_part2/webresources/client");
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            NodeList clientsElements = document.getElementsByTagName("client");

            for (int i = 0; i < clientsElements.getLength(); i++) {
                Node myClientNode = clientsElements.item(i);
                NodeList childNodeList = myClientNode.getChildNodes();

                MyClient c = new MyClient();

                for (int j = 0; j < childNodeList.getLength(); j++) {
                    // check by name and add to variables
                    if (childNodeList.item(j).getNodeName().equals("idclient")) {
                        c.setId(Integer.parseInt(childNodeList.item(j).getTextContent()));
                    } else if (childNodeList.item(j).getNodeName().equals("ip")) {
                        c.setIp(childNodeList.item(j).getTextContent());
                    } else if (childNodeList.item(j).getNodeName().equals("model")) {
                        c.setModel(childNodeList.item(j).getTextContent());
                    } else if (childNodeList.item(j).getNodeName().equals("type")) {
                        c.setType(childNodeList.item(j).getTextContent());
                    } else if (childNodeList.item(j).getNodeName().equals("addressList")) {
                        // process address and add it to addressList
                        Address a = new Address();
                        NodeList addressNodeList = childNodeList.item(j).getChildNodes();
                        // check by name and add to address properties
                        for (int k = 0; k < addressNodeList.getLength(); k++) {
                            if (addressNodeList.item(k).getNodeName().equals("city")) {
                                a.setCity(addressNodeList.item(k).getTextContent());
                            } else if (addressNodeList.item(k).getNodeName().equals("extra")) {
                                a.setExtra(addressNodeList.item(k).getTextContent());
                            } else if (addressNodeList.item(k).getNodeName().equals("flat")) {
                                a.setFlat(Integer.parseInt(addressNodeList.item(k).getTextContent()));
                            } else if (addressNodeList.item(k).getNodeName().equals("idaddress")) {
                                a.setId(Integer.parseInt(addressNodeList.item(k).getTextContent()));
                            } else if (addressNodeList.item(k).getNodeName().equals("num")) {
                                a.setNum(Integer.parseInt(addressNodeList.item(k).getTextContent()));
                            } else if (addressNodeList.item(k).getNodeName().equals("street")) {
                                a.setStreet(addressNodeList.item(k).getTextContent());
                            } else if (addressNodeList.item(k).getNodeName().equals("subnum")) {
                                a.setSubnum(Integer.parseInt(addressNodeList.item(k).getTextContent()));
                            }
                        }
                        c.addAddress(a);
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
        return clients;
    }
    
    void deleteClient(int id) {
        clientClient.remove(String.valueOf(id));
        
    }
    
    void deleteAddress(int id) {
        addressClient.remove(String.valueOf(id));
    }

}

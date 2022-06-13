/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class DemoDOM implements DemoDOMLocal {

    List<Client> clients = new ArrayList<>();

    @Override
    public void getClientsFromXMLDOM() {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("d:\\test\\Clients.xml"));

            NodeList clientsElements = document.getDocumentElement().getElementsByTagName("client");

            for (int i = 0; i < clientsElements.getLength(); i++) {
                Node client = clientsElements.item(i);
                NamedNodeMap attributes = client.getAttributes();

                Client c = new Client();

                c.setType(attributes.getNamedItem("type").getNodeValue());
                c.setModel(attributes.getNamedItem("model").getNodeValue());
                c.setIp(attributes.getNamedItem("ip").getNodeValue());
                clients.add(c);

            }
            
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");            
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> DOM PARSER RESULTS");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for(Client c : clients) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + c.getIp() + " " + c.getModel());
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DemoDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DemoDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemoDOM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

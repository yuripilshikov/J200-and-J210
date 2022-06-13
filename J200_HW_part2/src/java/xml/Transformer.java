package xml;

import entity.Client;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YuriPilshikov
 */
public class Transformer {
    
    // divide to 2 methods:
    // createXML - returns string with XML
    // saveXML - saves XML to file

    public String createXML(List<Client> clients) {
        
        // create text of the XML
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<clients>");
        for (Client client : clients) {
            String entry = "<client id=\"" + client.getIdclient()
                    + "\" type=\"" + client.getType()
                    + "\" model=\"" + client.getModel()
                    + "\" ip=\"" + client.getIp() + "\"/>";
            sb.append(entry);
        }
        sb.append("</clients>");
        
        return sb.toString();
    }
    
    public boolean saveToXML(String xml) {
        try (PrintWriter writer = new PrintWriter("d:\\test\\Clients.xml", "UTF-8")) {            
            writer.println(xml); 
            writer.close();
        } catch (FileNotFoundException ex) {
            return false;
        } catch (UnsupportedEncodingException ex) {
            return false;
        }
        return true;
    }
}

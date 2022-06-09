package beans;

import beans.repo.DbManagerLocal;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import entity.Address;
import entity.Client;

@Stateless
public class UpdateBean implements UpdateBeanLocal {
    
    @EJB
    ValidationBeanLocal validate;
    
    @EJB
    DbManagerLocal dbm;
    
    
    @Override
    public boolean addEntry(HttpServletRequest request) {
        
        String type = Objects.toString(request.getParameter("type"), "").trim();
        String model = Objects.toString(request.getParameter("model"), "").trim();
        String ip = Objects.toString(request.getParameter("ip"), "").trim();
        
        String city = Objects.toString(request.getParameter("city"), "").trim();
        String street = Objects.toString(request.getParameter("street"), "").trim();
        String numRaw = Objects.toString(request.getParameter("num"), "").trim();
        String subNumRaw = Objects.toString(request.getParameter("subnum"), "").trim();
        String flatRaw = Objects.toString(request.getParameter("flat"), "").trim();
        String extra = Objects.toString(request.getParameter("extra"), "").trim();         
        
        boolean testClient = validate.checkClientFields(request, type, model, ip);
        boolean testAddress = validate.checkAddressFields(request, city, street, numRaw);
        
        if(!testClient || !testAddress) return false;
                
        int num = Integer.parseInt(numRaw);                
        
        int subnum = 0;
        try {
            subnum = Integer.parseInt(subNumRaw);
        } catch(NumberFormatException e) {
            subnum = -1;
        }        
        
        int flat = 0;
        try {
            flat = Integer.parseInt(flatRaw);
        } catch(NumberFormatException e) {
            flat = -1;
        }
        
        Client c = new Client();
        c.setModel(model);
        c.setType(type);
        c.setIp(ip);
        dbm.persistClient(c);         
        
        Address a = new Address();
        a.setCity(city);
        a.setExtra(extra);
        a.setFlat(flat);
        a.setNum(num);
        a.setStreet(street);
        a.setDevice(c);
        dbm.persistAddress(a);
      
        return true;
    }

    @Override
    public boolean addAddress(HttpServletRequest request) {
        
        int clientId = 0;
        String clientIdRaw = Objects.toString(request.getParameter("clientToAddAddress"), "");  
        
        if (clientIdRaw.length() > 0) {
            clientId = Integer.parseInt(clientIdRaw);
        } else {
            request.setAttribute("msgError", "Не выбран клиент");
            return false;
        }
        
        String city = Objects.toString(request.getParameter("city"), "").trim();
        String street = Objects.toString(request.getParameter("street"), "").trim();        
        String numRaw = Objects.toString(request.getParameter("num"), "").trim();
        String subNumRaw = Objects.toString(request.getParameter("subnum"), "").trim();
        String flatRaw = Objects.toString(request.getParameter("flat"), "").trim();
        String extra = Objects.toString(request.getParameter("extra"), "").trim();
        
        boolean testAddress = validate.checkAddressFields(request, city, street, numRaw);
        if(!testAddress) return false;
        
        int num = Integer.parseInt(numRaw);                
        
        int subnum = 0;
        try {
            subnum = Integer.parseInt(subNumRaw);
        } catch(NumberFormatException e) {
            subnum = -1;
        }        
        
        int flat = 0;
        try {
            flat = Integer.parseInt(flatRaw);
        } catch(NumberFormatException e) {
            flat = -1;
        }

        Client c = dbm.getClientByID(clientId);

        Address a = new Address();
        a.setCity(city);
        a.setExtra(extra);
        a.setFlat(flat);
        a.setNum(num);
        a.setSubnum(subnum);
        a.setStreet(street);
        a.setDevice(c);
        dbm.persistAddress(a);

        return true;
    }

    @Override
    public boolean updateEntry(HttpServletRequest request) {
        int clientId = Integer.parseInt(request.getParameter("cliId").trim());
        Client c = dbm.getClientByID(clientId);
         
        String type = Objects.toString(request.getParameter("type"), "").trim();
        String model = Objects.toString(request.getParameter("model"), "").trim();
        String ip = Objects.toString(request.getParameter("ip"), "").trim();
        String city = Objects.toString(request.getParameter("city"), "").trim();
        String street = Objects.toString(request.getParameter("street"), "").trim();
        String numRaw = Objects.toString(request.getParameter("num"), "").trim();
        String subNumRaw = Objects.toString(request.getParameter("subnum"), "").trim();
        String flatRaw = Objects.toString(request.getParameter("flat"), "").trim();        
        String extra = Objects.toString(request.getParameter("extra"), "").trim();
        
        boolean testClient = validate.checkClientFields(request, type, model, ip);
        boolean testAddress = validate.checkAddressFields(request, city, street, numRaw);
        
        if(!testClient || !testAddress) return false;
        
        
        int num = 0;
        try {
            num = Integer.parseInt(numRaw);
        } catch(NumberFormatException e) {
            request.setAttribute("msgError", "Пожалуйста, указывайте номер дома цифрами");
            return false;
        }        
        
        int subnum = 0;
        try {
            subnum = Integer.parseInt(subNumRaw);
        } catch(NumberFormatException e) {
            subnum = -1;
        }              
        
        int flat = 0;
        try {
            flat = Integer.parseInt(flatRaw);
        } catch(NumberFormatException e) {
            flat = -1;
        }
        
        c.setModel(model);
        c.setType(type);
        c.setIp(ip);
        dbm.persistClient(c);
        
        Address a = dbm.getAddressByID(Integer.parseInt(request.getParameter("addrId")));
                
        a.setCity(city);        
        a.setStreet(street);
        a.setNum(num);
        a.setSubnum(subnum);  
        a.setFlat(flat);        
        a.setExtra(extra);
        a.setDevice(c);
        dbm.persistAddress(a);
        
        return true;
    }

    @Override
    public boolean deleteEntry(HttpServletRequest request) {
        int cid = 0;
        try {
            cid = Integer.parseInt(request.getParameter("cliId"));
        } catch (NumberFormatException e) {
            request.setAttribute("msgError", "Не могу получить ID клиента");
            return false;
        }        
        Client c = dbm.getClientByID(cid);
//        Client c = Client.getById(cid);
        
        int aid = 0;
        try {
            aid = Integer.parseInt(request.getParameter("addrId"));
        } catch (NumberFormatException e) {
            request.setAttribute("msgError", "Не могу получить ID адреса");
            return false;
        }        
        Address a = dbm.getAddressByID(aid);
//        Address a = c.getAddressById(aid);
        
        dbm.deleteAddress(a);
        if(c.getAddressList().isEmpty()) {
            dbm.deleteClient(c);
        }

//        c.getAddresses().remove(a);
//        if(c.getAddresses().isEmpty()) {
//            Client.listOfClients.remove(c);
//        }
        return true;
    }
}

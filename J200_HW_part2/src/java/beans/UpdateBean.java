/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import model.Address;
import model.Client;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class UpdateBean implements UpdateBeanLocal {
    
    @Override
    public boolean addEntry(HttpServletRequest request) {      
        int cId = 0;
        for(Client c : Client.listOfClients) {
            cId = cId >= c.getIdClient() ? cId : c.getIdClient();
        }
        
        String type = Objects.toString(request.getParameter("type"), "").trim();
        if(type.isEmpty()) {
            request.setAttribute("msgError", "Тип не указан");
            return false;
        }
        if(type.length() > 100) {
            request.setAttribute("msgError", "Размер поля ТИП превышает допустимое значение (100)");
            return false;
        }        
        if(type.replaceAll("[a-zA-Z0-9!-_ ]", "").length() > 0) {
            request.setAttribute("msgError", "В поле ТИП допустимы только латинские символы и цифры");
            return false;
        }
        
        String model = Objects.toString(request.getParameter("model"), "").trim();
        if(model.isEmpty()) {
            request.setAttribute("msgError", "Модель не указана");
            return false;
        }
        if(model.length() > 100) {
            request.setAttribute("msgError", "Размер поля МОДЕЛЬ превышает допустимое значение (100)");
            return false;
        }
        if(model.replaceAll("[a-zA-Z0-9!-_ ]", "").length() > 0) {
            request.setAttribute("msgError", "В поле ТИП допустимы только латинские символы и цифры");
            return false;
        }
        
        String ip = Objects.toString(request.getParameter("ip"), "").trim();
        if(ip.isEmpty()) {
            request.setAttribute("msgError", "IP адрес не указан");
            return false;
        }
        if(ip.length() > 25) {
            request.setAttribute("msgError", "Размер поля IP АДРЕС превышает допустимое значение (25)");
            return false;
        }
        String[] parts = ip.split("\\.");
        if(parts.length != 4) {
            request.setAttribute("msgError", "Некорректное значение IP адреса");
            return false;
        }        
        for(String str : parts) {
            try {
                int i = Integer.parseInt(str);
                if((i < 0) || (i > 255)) {
                    request.setAttribute("msgError", "Некорректное значение IP адреса");
                    return false;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("msgError", "Некорректное значение IP адреса");
                return false;
            }
        }
        
        Client c = new Client(cId + 1, type, model, ip);
        
        int aId = 0;        
        String city = Objects.toString(request.getParameter("city"), "").trim();
        if(city.isEmpty()) {
            request.setAttribute("msgError", "Город не указан");
            return false;
        }
        
        String street = Objects.toString(request.getParameter("street"), "").trim();
        if(street.isEmpty()) {
            request.setAttribute("msgError", "Улица не указана");
            return false;
        }
        
        String numRaw = Objects.toString(request.getParameter("num"), "").trim();
        if(numRaw.isEmpty()) {
            request.setAttribute("msgError", "Номер дома не указан");
            return false;
        }
        int num = 0;
        try {
            num = Integer.parseInt(numRaw);
        } catch(NumberFormatException e) {
            request.setAttribute("msgError", "Пожалуйста, указывайте номер дома цифрами");
            return false;
        }
        
        String subNumRaw = Objects.toString(request.getParameter("subnum"), "").trim();
        int subnum = 0;
        try {
            subnum = Integer.parseInt(subNumRaw);
        } catch(NumberFormatException e) {
            subnum = -1;
        }
        
        String flatRaw = Objects.toString(request.getParameter("flat"), "").trim();
        int flat = 0;
        try {
            flat = Integer.parseInt(flatRaw);
        } catch(NumberFormatException e) {
            flat = -1;
        }

        String extra = Objects.toString(request.getParameter("extra"), "").trim();
        
        Address address = new Address(aId + 1, city, street, num, subnum, flat, extra);        
        c.addAddress(address);
        
        Client.listOfClients.add(c);
        
        return true;
    }

    @Override
    public boolean addAddress(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateEntry(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteEntry(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

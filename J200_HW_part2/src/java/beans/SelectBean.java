/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
public class SelectBean implements SelectBeanLocal {

    @Override
    public List<Client> getAllClients() {
        // this is simple...
        return Client.listOfClients;
    }

    @Override
    public Client getClient(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Client> filter(HttpServletRequest request) {
        List<Client> clients = getAllClients();
        String city = Objects.toString(request.getParameter("city"), "").trim();
        String streetAndNum = Objects.toString(request.getParameter("streetAndNum"), "").trim();
        String[] tempAddress = streetAndNum.split("\\s");
        String street = Objects.toString(tempAddress[0], "");
        int num = -1;
        if (tempAddress.length == 2) {
            try {
                num = Integer.parseInt(tempAddress[1]);
            } catch (NumberFormatException e) {
                num = -1;
            }
        }
        List<Client> tempClients = new LinkedList<>();
        for (Client c : clients) {
            List<Address> addresses = c.getAddresses();
            boolean found = false;
            for (Address a : addresses) {
                if (!a.getCity().toLowerCase().contains(city.toLowerCase())) {
                    continue;
                }
                if (!a.getStreet().toLowerCase().contains(street.toLowerCase())) {
                    continue;
                }
                if (num > 0 && a.getNum() != num) {
                    continue;
                }
                found = true;
            }
            if (found) {
                tempClients.add(c);
            }
        }
        if (!tempClients.isEmpty()) {
            clients = new ArrayList<>(tempClients);
        }
        return clients;
    }

    @Override
    public List<String> getAllCities() {
        List<String> allCities = new ArrayList<>();
        List<Client> clients = getAllClients();
        for (Client c : clients) {
            List<Address> addresses = c.getAddresses();
            for (Address a : addresses) {
                String s = a.getCity();
                allCities.add(s);
            }
        }
        Collections.sort(allCities);
        return allCities;
    }
}

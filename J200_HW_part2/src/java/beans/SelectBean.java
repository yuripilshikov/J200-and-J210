/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.repo.DbManagerLocal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import entity.Address;
import entity.Client;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import xml.Transformer;
import xml.dom.DemoDOM;
import xml.sax.DemoSAX;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class SelectBean implements SelectBeanLocal {

    @EJB
    DbManagerLocal dbm;

    @Override
    public List<Client> getAllClients() {
        return dbm.getAllClients();
    }

    @Override
    public List<Address> getAllAddresses() {
        return dbm.getAllAddresses();
    }

    @Override
    public List<Address> filter(HttpServletRequest request) {
        //List<Client> clients = getAllClients();
        List<Address> addresses = getAllAddresses();
        String city = Objects.toString(request.getParameter("city"), "").trim().toLowerCase();

//        String street = Objects.toString(tempAddress[0], "");
//        int num = -1;
//        if (tempAddress.length == 2) {
//            try {
//                num = Integer.parseInt(tempAddress[1]);
//            } catch (NumberFormatException e) {
//                num = -1;
//            }
//        }
        String streetAndNum = Objects.toString(request.getParameter("streetAndNum"), "").trim();
        if (streetAndNum.length() > 0) {
            String[] tempAddress = streetAndNum.split("\\s");
            List<Address> tempAddresses = new LinkedList<>();

            for (Address a : addresses) {
                String tempAddr = a.getStreet().toLowerCase() + a.getNum();
                boolean test = true;
                for (String s : tempAddress) {
                    if (!tempAddr.contains(s.toLowerCase())) {
                        test = false;
                        break;
                    }
                }
                if (test) {
                    tempAddresses.add(a);
                }
            }
            addresses = new ArrayList<>(tempAddresses);
        }

        if (city.length() > 0) {
            List<Address> tempAddresses = new LinkedList<>();
            for (Address a : addresses) {

                if (a.getCity().toLowerCase().contains(city.toLowerCase())) {
                    tempAddresses.add(a);
                }
            }
//            if (!a.getStreet().toLowerCase().contains(street.toLowerCase())) {
//                continue;
//            }
//            if (num > 0 && a.getNum() != num) {
//                continue;
//            }
            

            addresses = new ArrayList<>(tempAddresses);
        }
        return addresses;

    }

    @Override
    public List<String> getAllCities() {
        List<String> allCities = new ArrayList<>();
        List<Address> addresses = getAllAddresses();
        for (Address a : addresses) {
            String s = a.getCity();
            allCities.add(s);
        }
        Collections.sort(allCities);

        // remove doubles
        Set<String> set = new HashSet<>(allCities);
        allCities.clear();
        allCities.addAll(set);

        return allCities;
    }

    @Override
    public List<Client> getFliteredClients(String filterName) {        
        Transformer.createXMLDOM(getAllClients());                
        return DemoDOM.getClientsFromXMLDOM(filterName);
    }

    @Override
    public List<Client> getFilteredClientsSAX(String filterName) {
        Transformer.createXML(getAllClients());
        return DemoSAX.getClientsFromXMLSAX(filterName);
    }
    
    
    
    
}

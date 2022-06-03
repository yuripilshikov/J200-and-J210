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
import java.util.LinkedList;

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

    public List<Address> getAllAddresses() {
        return dbm.getAllAddresses();
    }

    @Override
    public List<Address> filter(HttpServletRequest request) {
        //List<Client> clients = getAllClients();
        List<Address> addresses = getAllAddresses();
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

        List<Address> tempAddresses = new LinkedList<>();
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
            tempAddresses.add(a);

        }
        addresses = new ArrayList<>(tempAddresses);
        return tempAddresses;

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
        return allCities;
    }
}

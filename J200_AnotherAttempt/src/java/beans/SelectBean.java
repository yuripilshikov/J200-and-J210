package beans;

import beans.repo.DbManagerLocal;
import entities.Address;
import entities.Client;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

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

        List<Address> addresses = getAllAddresses();
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

}

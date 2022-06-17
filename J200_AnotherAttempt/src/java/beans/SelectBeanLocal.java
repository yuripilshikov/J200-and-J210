/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Address;
import entities.Client;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface SelectBeanLocal {
    List<Client> getAllClients();
    List<Address> getAllAddresses();
    List<Address> filter(HttpServletRequest request);
    
    List<String> getAllCities();
}

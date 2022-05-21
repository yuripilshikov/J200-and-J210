/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import model.Client;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface SelectBeanLocal {
    List<Client> getAllClients();
    Client getClient(HttpServletRequest request);
    List<Client> filter(HttpServletRequest request);
    
    List<String> getAllCities();
}

package beans;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import entity.Address;
import entity.Client;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface SelectBeanLocal {
    List<Client> getAllClients();
    List<Address> getAllAddresses();
//    Client getClient(HttpServletRequest request);
    List<Address> filter(HttpServletRequest request);
    
    
    List<String> getAllCities();
}

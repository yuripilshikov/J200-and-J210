package beans.repo;

import entities.Address;
import entities.Client;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface DbManagerLocal {
    List<Client> getAllClients();
    List<Address> getAllAddresses();
    
    Client getClientByID(int id);
    Address getAddressByID(int id);
    
    void persistClient(Client client);
    void persistAddress(Address address);
    
    void deleteClient(Client client);
    void deleteAddress(Address address);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.repo;

import java.util.List;
import javax.ejb.Local;
import entity.Address;
import entity.Client;

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
    
    int persistClient(Client client);
    int persistAddress(Address address);
    
    void deleteClient(Client client);
    void deleteAddress(Address address);
}

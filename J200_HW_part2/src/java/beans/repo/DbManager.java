/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.repo;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Address;
import entity.Client;

/**
 *
 * @author YuriPilshikov
 */
@Singleton
public class DbManager implements DbManagerLocal {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Client> getAllClients() {
        return em.createNamedQuery("Client.findAll").getResultList();
    }

    @Override
    public List<Address> getAllAddresses() {
        return em.createNamedQuery("Address.findAll").getResultList();
    }

    @Override
    public int persistClient(Client client) {
        em.persist(client);
        em.flush();
        return client.getIdclient();
    }

    @Override
    public int persistAddress(Address address) {
        em.persist(address);
        em.flush();
        return address.getIdaddress();
    }
    
    @Override
    public Client getClientByID(int id) {
        return em.find(Client.class, id);        
    }

    @Override
    public Address getAddressByID(int id) {
        return em.find(Address.class, id);
    }

    @Override
    public void deleteClient(Client client) {
        em.remove(client);
        em.flush();
    }
    
    @Override
    public void deleteAddress(Address address) {
        em.remove(address);
        em.flush();
    }
}

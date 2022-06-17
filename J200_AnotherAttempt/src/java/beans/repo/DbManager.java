package beans.repo;

import entities.Address;
import entities.Client;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public Client getClientByID(int id) {
        return em.find(Client.class, id);
    }

    @Override
    public Address getAddressByID(int id) {
        return em.find(Address.class, id);
    }

    @Override
    public void persistClient(Client client) {        
        em.persist(client);
        em.flush();        
    }

    @Override
    public void persistAddress(Address address) {
        em.persist(address);
        em.flush();
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

package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YuriPilshikov
 */
public class Client {
    private int idClient;   // 100 characters
    private String type;    // 100 characters
    private String model;   // 100 characters
    private String ip;      // 25 characters
    private List<Address> addresses;
    
    public static List<Client> listOfClients;
    
    static {
        listOfClients = new ArrayList<>();
        Client c = new Client(0, "Router", "NetGear 123", "192.168.80.11");
        c.addAddress(Address.listOfAddresses.get(0));
        listOfClients.add(c);
        listOfClients.add(new Client(0, "Server", "Synology", "192.168.80.12"));
        listOfClients.add(new Client(0, "NAS", "XPEnology", "192.168.80.13"));
        listOfClients.add(new Client(0, "Workstation", "Lenovo ThinkPad 123", "192.168.80.22"));
        listOfClients.add(new Client(0, "NAS", "Apple TimeCapsule", "192.168.80.250"));                
    }

    public Client(int idClient, String type, String model, String ip) {
        this.idClient = idClient;
        this.type = type;
        this.model = model;
        this.ip = ip;
        this.addresses = new ArrayList<>();
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void addAddress(Address address) {
        this.addresses.add(address);
    }
    
    public void removeAddress(Address address) {        
        this.addresses.remove(address);        
    }
    
    public static Client addClient(int idClient, String type, String model, String ip, List<Address> addresses) {
        int id = 0;
        for(Client c : listOfClients) {
            id = c.getIdClient() > id ? c.getIdClient() : id;
        }
        Client client = new Client(idClient, type, model, ip);
        return client;
    }

    public String getAddressListAsString() {
        if(this.addresses == null || this.addresses.isEmpty()) return "n/a";
        StringBuilder sb = new StringBuilder();
        for(Address a : this.addresses) {
            sb.append(a.toString()).append("; ");
        }
        return sb.toString();
    }
}

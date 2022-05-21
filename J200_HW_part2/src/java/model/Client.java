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
        listOfClients.add(new Client(0, "Router", "NetGear 123", "192.168.80.11", new Address(0, "Санкт-Петербург", "Улица Ленина", 1, 2, 3, "Here be some text")));        
        listOfClients.add(new Client(1, "NAS", "XPEnology", "192.168.80.13", new Address(0, "Москва", "Улица Ленина", 1, 2, 13, "Here be some text")));
        listOfClients.add(new Client(2, "Workstation", "Lenovo ThinkPad 123", "192.168.80.22", new Address(0, "Мга", "Улица Правды", 1, 5, 2, "")));
        listOfClients.add(new Client(3, "NAS", "Apple TimeCapsule", "192.168.80.250", new Address(0, "Воронеж", "улица Льва Толстого", 1, 2, 3, "")));
        listOfClients.get(1).addAddress(new Address(9, "Пекин", "улица Конфуция", 1, 2, 3, ""));
    }

    public Client(int idClient, String type, String model, String ip) {
        this.idClient = idClient;
        this.type = type;
        this.model = model;
        this.ip = ip;
        this.addresses = new ArrayList<>();
    }

    public Client(int idClient, String type, String model, String ip, Address address) {
        this(idClient, type, model, ip);
        this.addresses.add(address);
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
    
    public static Client getById(int id) {
        for(Client c : listOfClients) {
            if(c.getIdClient() == id) return c;
        }
        return null;
    }
    
    public Address getAddressById(int id) {
        for(Address a : addresses) {
            if(a.getIdAddress() == id) return a;
        }
        return null;
    }

    @Override
    public String toString() {
        return idClient + ", " + type + " " + model + " " + ip;
    }   
}

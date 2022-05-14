package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YuriPilshikov
 */
public class Address {
    private int idAddress;
    private String city;    // 100 characters
    private String street;  // 100 characters
    private int num;
    private int subnum;
    private int flat;
    private String extra;   // 200 characters
    
    public static List<Address> listOfAddresses;
    
    static {
        listOfAddresses = new ArrayList<>();
        listOfAddresses.add(new Address(0, "Санкт-Петербург", "Улица Ленина", 1, 2, 3, "Here be some text"));
        listOfAddresses.add(new Address(0, "Москва", "Улица Ленина", 1, 2, 13, "Here be some text"));
        listOfAddresses.add(new Address(0, "Мга", "Улица Правды", 1, 5, 2, ""));
        listOfAddresses.add(new Address(0, "Воронеж", "улица Льва Толстого", 1, 2, 3, ""));
        listOfAddresses.add(new Address(0, "Улан-Батор", "улица Пушкина", 1, 2, 3, ""));
        listOfAddresses.add(new Address(0, "Приозерск", "Ленинградское шоссе", 3, 2, 1, ""));
        listOfAddresses.add(new Address(0, "Приозерск", "улица Мира", 1, 2, 3, "Здесь что-то написано"));        
    }

    public Address(int id, String city, String street, int num, int subnum, int flat, String extra) {
        this.idAddress = id;
        this.city = city;
        this.street = street;
        this.num = num;
        this.subnum = subnum;
        this.flat = flat;
        this.extra = extra;
        // idAddress - to be obtained from the database
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSubnum() {
        return subnum;
    }

    public void setSubnum(int subnum) {
        this.subnum = subnum;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return idAddress + " " + city + " " + street + ", " + num + "/" + subnum + "/" + flat;
    }
    
    
    
    public Address addAddress(int id, String city, String street, int num, int subnum, int flat, String extra) {
        int idA = 0;
        for(Address d : listOfAddresses) {            
            idA = d.getIdAddress()> idA ? d.getIdAddress(): idA;
        }
        Address address = new Address(id, city, street, num, subnum, flat, extra);
        return address;
    }     
}

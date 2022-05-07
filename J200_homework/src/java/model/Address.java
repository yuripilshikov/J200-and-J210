package model;

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

    public Address(String city, String street, int num, int subnum, int flat, String extra) {
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
    
    
}

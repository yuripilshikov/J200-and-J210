/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author YuriPilshikov
 */
@Entity
@Table(name = "ADDRESS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
    , @NamedQuery(name = "Address.findByIdaddress", query = "SELECT a FROM Address a WHERE a.idaddress = :idaddress")
    , @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city")
    , @NamedQuery(name = "Address.findByStreet", query = "SELECT a FROM Address a WHERE a.street = :street")
    , @NamedQuery(name = "Address.findByNum", query = "SELECT a FROM Address a WHERE a.num = :num")
    , @NamedQuery(name = "Address.findByFlat", query = "SELECT a FROM Address a WHERE a.flat = :flat")
    , @NamedQuery(name = "Address.findByExtra", query = "SELECT a FROM Address a WHERE a.extra = :extra")
    , @NamedQuery(name = "Address.findBySubnum", query = "SELECT a FROM Address a WHERE a.subnum = :subnum")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDADDRESS")
    private Integer idaddress;    
    @Column(name = "CITY", length = 100)
    private String city;    
    @Column(name = "STREET", length = 100)
    private String street;
    @Basic(optional = false)    
    @Column(name = "NUM")
    private int num;
    @Column(name = "FLAT")
    private Integer flat;    
    @Column(name = "EXTRA", length = 200)
    private String extra;
    @Column(name = "SUBNUM")
    private Integer subnum;
    @JoinColumn(name = "DEVICE", referencedColumnName = "IDCLIENT")
    @ManyToOne
    private Client device;

    public Address() {
    }

    public Address(Integer idaddress) {
        this.idaddress = idaddress;
    }

    public Address(Integer idaddress, int num) {
        this.idaddress = idaddress;
        this.num = num;
    }

    public Integer getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(Integer idaddress) {
        this.idaddress = idaddress;
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

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getSubnum() {
        return subnum;
    }

    public void setSubnum(Integer subnum) {
        this.subnum = subnum;
    }

    @XmlTransient
    public Client getDevice() {
        return device;
    }

    public void setDevice(Client device) {
        this.device = device;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaddress != null ? idaddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.idaddress == null && other.idaddress != null) || (this.idaddress != null && !this.idaddress.equals(other.idaddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Address[ idaddress=" + idaddress + " ]";
    }
    
}

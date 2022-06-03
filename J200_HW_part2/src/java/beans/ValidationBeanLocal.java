/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface ValidationBeanLocal {
    // client
    String checkClientType(String type);
    String checkClientModel(String model);
    String checkClientIp(String ip);
    
    // address
    String checkAddressCity(String city);
    String checkAddressStreet(String street);
    String checkAddressNum(String numRaw);

    public boolean checkClientFields(HttpServletRequest request, String type, String model, String ip);

    public boolean checkAddressFields(HttpServletRequest request, String city, String street, String numRaw);
}

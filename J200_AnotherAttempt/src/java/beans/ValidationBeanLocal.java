package beans;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface ValidationBeanLocal {

    String checkClientType(String type);
    String checkClientModel(String model);
    String checkClientIp(String ip);

    String checkAddressCity(String city);
    String checkAddressStreet(String street);
    String checkAddressNum(String numRaw);

    public boolean checkClientFields(HttpServletRequest request, String type, String model, String ip);

    public boolean checkAddressFields(HttpServletRequest request, String city, String street, String numRaw);
}

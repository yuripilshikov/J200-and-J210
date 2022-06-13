package beans;

import entity.Client;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface DemoSAXLocal {
    public void getClientsFromXMLSAX();
}

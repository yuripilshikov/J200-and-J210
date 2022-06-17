package beans;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface UpdateBeanLocal {
    boolean addEntry(HttpServletRequest request);
    boolean addAddress(HttpServletRequest request);
    boolean updateEntry(HttpServletRequest request);
    boolean deleteEntry(HttpServletRequest request);
}

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Address;
import model.Client;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "ViewList", urlPatterns = {"/viewlist"})
public class ViewList extends HttpServlet {
    
    private List<Address> addresses;
    private List<Client> clients;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.addresses = Address.listOfAddresses;
        this.clients = Client.listOfClients;
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewList</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Список адресов</h1>\n");
            out.println("<table border=\"1\">\n");
            out.println("<tr>\n");
            out.println("<th>ID</th>\n");
            out.println("<th>Город</th>\n");
            out.println("<th>Улица</th>\n");
            out.println("<th>Номер дома</th>\n");
            out.println("<th>Номер корпуса</th>\n");
            out.println("<th>Номер квартиры</th>\n");
            out.println("<th>Дополнительно</th>\n");
            out.println("</tr>\n");
            for(Address a : addresses) {
                out.println("<tr>\n");
                out.println("<td>" + a.getIdAddress()+ "</td>\n");
                out.println("<td>" + a.getCity() + "</td>\n");
                out.println("<td>" + a.getStreet()+ "</td>\n");
                out.println("<td>" + a.getNum()+ "</td>\n");
                out.println("<td>" + a.getSubnum()+ "</td>\n");
                out.println("<td>" + a.getFlat()+ "</td>\n");
                out.println("<td>" + a.getExtra()+ "</td>\n");               
                out.println("</tr>\n");
            }            
            out.println("</table>");
            
            out.println("<h1>Список клиентов</h1>\n");
            out.println("<table border=\"1\">\n");
            out.println("<tr>\n");
            out.println("<th>ID</th>\n");
            out.println("<th>Тип</th>\n");
            out.println("<th>Модель</th>\n");
            out.println("<th>IP адрес</th>\n");
            out.println("<th>Список адресов</th>\n");            
            out.println("</tr>\n");
            for(Client c : clients) {
                out.println("<tr>\n");
                out.println("<td>" + c.getIdClient()+ "</td>\n");
                out.println("<td>" + c.getType()+  "</td>\n");
                out.println("<td>" + c.getModel()+ "</td>\n");
                out.println("<td>" + c.getIp()+ "</td>\n");
                out.println("<td>" + c.getAddressListAsString()+ "</td>\n");                
                out.println("</tr>\n");
            }
            
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

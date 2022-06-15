package servlets;

import beans.SelectBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Address;
import entity.Client;
import xml.Transformer;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "ViewList", urlPatterns = {"/*"})
public class ViewList extends HttpServlet {

    @EJB
    SelectBeanLocal selectLocal;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Address> addresses = selectLocal.filter(request);
        List<Client> clients = selectLocal.getAllClients();
        
        
        //Transformer.createXML(clients);
        Transformer.createXMLDOM(clients);
        
        
        

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Список клиентов и их адресов</h1>\n");
            out.println("<table border=\"1\">\n");
            out.println("<tr>\n");
            out.println("<th>ID</th>\n");
            out.println("<th>Тип</th>\n");
            out.println("<th>Модель</th>\n");
            out.println("<th>IP адрес</th>\n");
            out.println("<th>Город</th>\n");
            out.println("<th>Улица</th>\n");
            out.println("<th>Дом</th>\n");
            out.println("<th>Корпус</th>\n");
            out.println("<th>Квартира</th>\n");
            out.println("<th>Примечание</th>\n");
            out.println("<th>Редактировать</th>\n");
            out.println("</tr>\n");

            for (Address a : addresses) {
                out.println("<tr>");
                out.println("<td>" + a.getDevice().getIdclient() + "</td>");
                out.println("<td>" + a.getDevice().getType() + "</td>\n");
                out.println("<td>" + a.getDevice().getModel() + "</td>\n");
                out.println("<td>" + a.getDevice().getIp() + "</td>\n");
                out.println("<td>" + a.getCity() + "</td>\n");
                out.println("<td>" + a.getStreet() + "</td>\n");
                out.println("<td>" + a.getNum() + "</td>\n");
                out.println("<td>" + Objects.toString(a.getSubnum(), "") + "</td>\n");
                out.println("<td>" + ((a.getFlat() == -1) ? "" : a.getFlat()) + "</td>\n");
                out.println("<td>" + Objects.toString(a.getExtra(), "") + "</td>\n");
                out.println("<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + a.getDevice().getIdclient() + "&aid=" + a.getIdaddress() + "\">" + "Редактировать" + "</a>"
                        + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + a.getDevice().getIdclient() + "&aid=" + a.getIdaddress() + "\">" + "Удалить" + "</a>"
                        + "</td></tr>");
            }
            out.println("</table>");

            out.println("<form action =\"viewlist2\" method=\"GET\">\n");
            out.println("<h2>ФИЛЬТР</h2>\n");
            out.println("<label for=\"city\">Город: </label>");
            out.println("<select name=\"city\">");
            List<String> allCities = selectLocal.getAllCities();
            out.println("<option value=\"\"></option>");
            for (String s : allCities) {
                out.println("<option value=\"" + s + "\">" + s + "</option>");
            }
            out.println("</select>");
            out.println("<label for=\"streetAndNum\">Улица и номер дома: </label><input type=\"text\" name=\"streetAndNum\" value=\"" + Objects.toString(request.getParameter("streetAndNum"), "") + "\" /><br><br>\n");
            out.println("<input type=\"submit\" value=\"ПРИМЕНИТЬ ФИЛЬТР\" /><br><br>\n");
            out.println("</form>\n");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\"/><br>");
            out.println("<h1>УПРАВЛЕНИЕ БАЗОЙ</h1>\n");
            out.println("<form action =\"create2\" method=\"POST\">\n");
            out.println("<p>\n");
            out.println("<input type=\"submit\" value=\"Создать новую запись устройства в базе данных\"/>\n");
            out.println("</p>\n");
            out.println("</form>");
            out.println("<form action =\"addaddress2\" method=\"POST\">\n");
            out.println("<p>\n");
            out.println("<input type=\"submit\" value=\"Добавить адрес существующему устройству\"/>\n");
            out.println("</p>\n");
            out.println("</form>");

            out.println("<h1>EXPERIMENTAL Сведения о клиенте - SAX</h1>\n"
                    + "        <form action=\"CheckSAX\" method=\"POST\">\n"
                    + "            <label for=\"modelText\">Ввести параметр model: </label><input type=\"text\" name=\"modelText\" />\n"
                    + "            <input type=\"submit\" value=\"Показать сведения\"/>\n"
                    + "        </form>");
            
            out.println("<h1>EXPERIMENTAL Сведения о клиенте - DOM</h1>\n"
                    + "        <form action=\"CheckDOM\" method=\"POST\">\n"
                    + "            <label for=\"modelText\">Ввести параметр model: </label><input type=\"text\" name=\"modelText\" />\n"
                    + "            <input type=\"submit\" value=\"Показать сведения\"/>\n"
                    + "        </form>");
            
            

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }
}

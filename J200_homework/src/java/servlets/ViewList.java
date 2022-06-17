package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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

    private List<Client> clients;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.clients = filter(request);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Список клиентов и их адресов</h1>\n");
            out.println("<table border=\"1\">\n");
            out.println("<tr>\n");
            out.println("<th rowspan=\"2\">ID</th>\n");
            out.println("<th rowspan=\"2\">Тип</th>\n");
            out.println("<th rowspan=\"2\">Модель</th>\n");
            out.println("<th rowspan=\"2\">IP адрес</th>\n");
            out.println("<th colspan = \"7\">Адрес</th>\n");
            out.println("<th rowspan=\"2\">Редактировать</th>\n");
            out.println("</tr>\n");
            out.println("<tr>\n");
            out.println("<th>Номер</th>\n");
            out.println("<th>Город</th>\n");
            out.println("<th>Улица</th>\n");
            out.println("<th>Дом</th>\n");
            out.println("<th>Корпус</th>\n");
            out.println("<th>Квартира</th>\n");            
            out.println("<th>Примечание</th>\n");
            out.println("</tr>\n");
            for (Client c : clients) {
                out.println(createRow(c));
            }
            out.println("</table>");

            out.println("<form action =\"viewlist\" method=\"GET\">\n");
            out.println("<h2>ФИЛЬТР</h2>\n");
            //out.println("<label for=\"city\">Город: </label><input type=\"text\" name=\"city\" value=\"" + Objects.toString(request.getParameter("city"), "") + "\" /><br><br>\n");
            out.println("<label for=\"city\">Город: </label>");
            out.println("<select name=\"city\">");
            List<String> allCities = getAllCities();
            out.println("<option value=\"\"></option>");
            for (String s : allCities) {
                out.println("<option value=\"" + s + "\">" + s + "</option>");
            }
            out.println("</select>");

            out.println("<label for=\"streetAndNum\">Улица и номер дома: </label><input type=\"text\" name=\"streetAndNum\" value=\"" + Objects.toString(request.getParameter("streetAndNum"), "") + "\" /><br><br>\n");
            out.println("<input type=\"submit\" value=\"ПРИМЕНИТЬ ФИЛЬТР\" /><br><br>\n");
            out.println("</form>\n");

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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String createRow(Client c) {

        String s = null;
        int as = c.getAddresses().size();

        if (as > 1) {
            StringBuilder sb = new StringBuilder();
            s = "<tr>\n" + "<td rowspan=\" " + as + "\">"
                    + c.getIdClient() + "</td>\n"
                    + "<td rowspan=\"" + as + "\">" + c.getType() + "</td>\n"
                    + "<td rowspan=\"" + as + "\">" + c.getModel() + "</td>\n"
                    + "<td rowspan=\"" + as + "\">" + c.getIp() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getIdAddress() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getCity() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getStreet() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getNum() + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getSubnum() < 0 ? "-" : c.getAddresses().get(0).getSubnum()) + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getFlat() < 0 ? "-" : c.getAddresses().get(0).getFlat()) + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getExtra() + "</td>\n"
                    + "<td><a href=\"http://localhost:26213/J200_homework/update?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Редактировать" + "</a>"
                    + "<a href=\"http://localhost:26213/J200_homework/delete?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Удалить" + "</a>"
                    + "</td>\n"
                    + "</tr>\n";
            sb.append(s);
            for (int i = 1; i < as; i++) {
                s = "<tr>"
                        + "<td>" + c.getAddresses().get(i).getIdAddress() + "</td>\n"
                        + "<td>" + c.getAddresses().get(i).getCity() + "</td>\n"
                        + "<td>" + c.getAddresses().get(i).getStreet() + "</td>\n"
                        + "<td>" + c.getAddresses().get(i).getNum() + "</td>\n"
                        + "<td>" + (c.getAddresses().get(i).getSubnum() < 0 ? "-" : c.getAddresses().get(i).getSubnum()) + "</td>\n"
                        + "<td>" + (c.getAddresses().get(i).getFlat() < 0 ? "-" : c.getAddresses().get(i).getFlat()) + "</td>\n"
                        + "<td>" + c.getAddresses().get(i).getExtra() + "</td>\n"
                        + "<td><a href=\"http://localhost:26213/J200_homework/update?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(i).getIdAddress() + "\">" + "Редактировать" + "</a>"
                        + "<a href=\"http://localhost:26213/J200_homework/delete?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(i).getIdAddress() + "\">" + "Удалить" + "</a>"
                        + "</td>\n"
                        + "</tr>\n";
                sb.append(s);
            }
            s = sb.toString();
        } else {
            s = "<tr>\n" + "<td>"
                    + c.getIdClient() + "</td>\n"
                    + "<td>" + c.getType() + "</td>\n"
                    + "<td>" + c.getModel() + "</td>\n"
                    + "<td>" + c.getIp() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getIdAddress() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getCity() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getStreet() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getNum() + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getSubnum() < 0 ? "" : c.getAddresses().get(0).getSubnum()) + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getFlat() < 0 ? "" : c.getAddresses().get(0).getFlat()) + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getExtra() + "</td>\n"
                    + "<td><a href=\"http://localhost:26213/J200_homework/update?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Редактировать" + "</a>"
                    + "<a href=\"http://localhost:26213/J200_homework/delete?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Удалить" + "</a>"
                    + "</td>\n"
                    + "</tr>\n";
        }
        return s;
    }

    private List<Client> filter(HttpServletRequest request) {
        List<Client> clients = Client.listOfClients;
        String city = Objects.toString(request.getParameter("city"), "").trim();
        String streetAndNum = Objects.toString(request.getParameter("streetAndNum"), "").trim();
        String[] tempAddress = streetAndNum.split("\\s");
        String street = Objects.toString(tempAddress[0], "");
        int num = -1;
        if (tempAddress.length == 2) {
            try {
                num = Integer.parseInt(tempAddress[1]);
            } catch (NumberFormatException e) {
                num = -1;
            }
        }
        List<Client> tempClients = new LinkedList<>();
        for (Client c : clients) {
            List<Address> addresses = c.getAddresses();
            boolean found = false;
            for (Address a : addresses) {
                if(!a.getCity().toLowerCase().contains(city.toLowerCase())) continue;
                if(!a.getStreet().toLowerCase().contains(street.toLowerCase())) continue;
                if(num > 0 && a.getNum() != num) continue;
                found = true;                
            }
            tempClients.add(c);
        }    
        if(!tempClients.isEmpty()) clients = new ArrayList<>(tempClients);
        return clients;
    }

    private List<String> getAllCities() {
        List<String> allCities = new ArrayList<>();
        List<Client> clients = Client.listOfClients;
        for (Client c : clients) {
            List<Address> addresses = c.getAddresses();
            for (Address a : addresses) {
                String s = a.getCity();
                allCities.add(s);
            }
        }
        Collections.sort(allCities);
        return allCities;
    }

}

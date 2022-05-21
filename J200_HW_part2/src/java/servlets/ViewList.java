package servlets;

import beans.RowBuilderBeanLocal;
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
import model.Client;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "ViewList", urlPatterns = {"/viewlist2"})
public class ViewList extends HttpServlet {
    
    @EJB
    SelectBeanLocal selectLocal;    
    
    @EJB
    RowBuilderBeanLocal rowBuilder;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Client> clients = selectLocal.filter(request);

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
                out.println(rowBuilder.createRow(c));
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
            out.println("<a href=\"http://localhost:26213/J200_HW_part2\">Вернуться к EntryPoint</a>");            
            out.println("<h1>Diagnostics</h1>");
            out.println("<p>");
            out.println(clients.size());
            out.println("</p>");                             
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

//    private String createRow(Client c) {

//        String s = null;
//        int as = c.getAddresses().size();
//
//        if (as > 1) {
//            StringBuilder sb = new StringBuilder();
//            s = "<tr>\n" + "<td rowspan=\" " + as + "\">"
//                    + c.getIdClient() + "</td>\n"
//                    + "<td rowspan=\"" + as + "\">" + c.getType() + "</td>\n"
//                    + "<td rowspan=\"" + as + "\">" + c.getModel() + "</td>\n"
//                    + "<td rowspan=\"" + as + "\">" + c.getIp() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getIdAddress() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getCity() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getStreet() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getNum() + "</td>\n"
//                    + "<td>" + (c.getAddresses().get(0).getSubnum() < 0 ? "-" : c.getAddresses().get(0).getSubnum()) + "</td>\n"
//                    + "<td>" + (c.getAddresses().get(0).getFlat() < 0 ? "-" : c.getAddresses().get(0).getFlat()) + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getExtra() + "</td>\n"
//                    + "<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Редактировать" + "</a>"
//                    + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Удалить" + "</a>"
//                    + "</td>\n"
//                    + "</tr>\n";
//            sb.append(s);
//            for (int i = 1; i < as; i++) {
//                s = "<tr>"
//                    + "<td>" + c.getAddresses().get(i).getIdAddress() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(i).getCity() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(i).getStreet() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(i).getNum() + "</td>\n"
//                    + "<td>" + (c.getAddresses().get(i).getSubnum() < 0 ? "-" : c.getAddresses().get(i).getSubnum()) + "</td>\n"
//                    + "<td>" + (c.getAddresses().get(i).getFlat() < 0 ? "-" : c.getAddresses().get(i).getFlat()) + "</td>\n"
//                    + "<td>" + c.getAddresses().get(i).getExtra() + "</td>\n"
//                    + "<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(i).getIdAddress() + "\">" + "Редактировать" + "</a>"
//                    + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(i).getIdAddress() + "\">" + "Удалить" + "</a>"
//                    + "</td>\n"
//                    + "</tr>\n";
//                sb.append(s);
//            }
//            s = sb.toString();
//        } else {
//            s = "<tr>\n" + "<td>"
//                    + c.getIdClient() + "</td>\n"
//                    + "<td>" + c.getType() + "</td>\n"
//                    + "<td>" + c.getModel() + "</td>\n"
//                    + "<td>" + c.getIp() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getIdAddress() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getCity() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getStreet() + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getNum() + "</td>\n"
//                    + "<td>" + (c.getAddresses().get(0).getSubnum() < 0 ? "" : c.getAddresses().get(0).getSubnum()) + "</td>\n"
//                    + "<td>" + (c.getAddresses().get(0).getFlat() < 0 ? "" : c.getAddresses().get(0).getFlat()) + "</td>\n"
//                    + "<td>" + c.getAddresses().get(0).getExtra() + "</td>\n"
//                    + "<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Редактировать" + "</a>"
//                    + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Удалить" + "</a>"
//                    + "</td>\n"
//                    + "</tr>\n";
//        }
//        return s;
//    }
}

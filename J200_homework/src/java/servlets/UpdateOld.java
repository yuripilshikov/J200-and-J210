package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "Update", urlPatterns = {"/update"})
public class UpdateOld extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int cid = 0;
        try {
            cid = Integer.parseInt(request.getParameter("cid"));
        } catch (NumberFormatException e) {
            response.sendRedirect("http://localhost:26213/J200_homework/viewlist");
        }
        
        Client c = Client.getById(cid);
        
        int aid = 0;
        try {
            aid = Integer.parseInt(request.getParameter("aid"));
        } catch (NumberFormatException e) {
            response.sendRedirect("http://localhost:26213/J200_homework/viewlist");
        }
        
        Address a = c.getAddressById(aid);
        
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Редактирование записи</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>РЕДАКТИРОВАНИЕ ЗАПИСИ</h1>\n");
            out.println("<form action =\"update\" method=\"POST\">\n");            
            out.println("<p>Данные клиента:</p>\n");
            out.println("<input type=\"hidden\" name=\"cliId\"  value=\" " + c.getIdClient() + " \" /><br>\n");
            out.println("<label for=\"type\">Тип</label><input type=\"text\" name=\"type\" required value=\"" + c.getType() + "\" /><br>\n");
            out.println("<label for=\"model\">Модель</label><input type=\"text\" name=\"model\" required value=\"" + c.getModel()+ "\" /><br>\n");
            out.println("<label for=\"ip\">IP</label><input type=\"text\" name=\"ip\" required value=\"" + c.getIp()+ "\" /><br> \n");
            out.println("<p>Данные адреса:</p>\n");
            out.println("<input type=\"hidden\" name=\"addrId\"  value=\"" + a.getIdAddress() + "\" /><br>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\" required value=\"" + a.getCity() + "\" /><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\" required value=\" " + a.getStreet() + " \" /><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\" required value=\"" + a.getNum() + "\" /><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\" value=\"" + a.getSubnum() + "\" /><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\" value=\"" + a.getFlat() + "\" /><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\" value=\"" + a.getExtra() + "\" /><br>\n");
            out.println("<input type=\"submit\" value=\"ВНЕСТИ ИЗМЕНЕНИЯ\"/> \n");
            out.println("</form>");
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
        
        // set new values
        if(modifyValues(request)) {
            response.sendRedirect("http://localhost:26213/J200_homework/viewlist");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private boolean modifyValues(HttpServletRequest request) {        
        Client c = Client.getById(Integer.parseInt(request.getParameter("cliId").trim()));
        
        
        String type = Objects.toString(request.getParameter("type"), "").trim();
        if(type.isEmpty()) {
            request.setAttribute("msgError", "Тип не указан");
            return false;
        }
        if(type.length() > 100) {
            request.setAttribute("msgError", "Размер поля ТИП превышает допустимое значение (100)");
            return false;
        }        
        if(type.replaceAll("[a-zA-Z0-9!-_ ]", "").length() > 0) {
            request.setAttribute("msgError", "В поле ТИП допустимы только латинские символы и цифры");
            return false;        
        }
        c.setType(type);
        
        String model = Objects.toString(request.getParameter("model"), "").trim();
        if(model.isEmpty()) {
            request.setAttribute("msgError", "Модель не указана");
            return false;
        }
        if(model.length() > 100) {
            request.setAttribute("msgError", "Размер поля МОДЕЛЬ превышает допустимое значение (100)");
            return false;
        }
        if(model.replaceAll("[a-zA-Z0-9!-_ ]", "").length() > 0) {
            request.setAttribute("msgError", "В поле ТИП допустимы только латинские символы и цифры");
            return false;
        }
        c.setModel(model);
        
        String ip = Objects.toString(request.getParameter("ip"), "").trim();
        if(ip.isEmpty()) {
            request.setAttribute("msgError", "IP адрес не указан");
            return false;
        }
        if(ip.length() > 25) {
            request.setAttribute("msgError", "Размер поля IP АДРЕС превышает допустимое значение (25)");
            return false;
        }
        String[] parts = ip.split("\\.");
        if(parts.length != 4) {
            request.setAttribute("msgError", "Некорректное значение IP адреса");
            return false;
        }        
        for(String str : parts) {
            try {
                int i = Integer.parseInt(str);
                if((i < 0) || (i > 255)) {
                    request.setAttribute("msgError", "Некорректное значение IP адреса");
                    return false;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("msgError", "Некорректное значение IP адреса");
                return false;
            }
        }
        c.setIp(ip);
        
        
        Address a = c.getAddressById(Integer.parseInt(request.getParameter("addrId")));
        
        String city = Objects.toString(request.getParameter("city"), "").trim();
        if(city.isEmpty()) {
            request.setAttribute("msgError", "Город не указан");
            return false;
        }
        a.setCity(city);
        
        String street = Objects.toString(request.getParameter("street"), "").trim();
        if(street.isEmpty()) {
            request.setAttribute("msgError", "Улица не указана");
            return false;
        }
        a.setStreet(street);
        
        String numRaw = Objects.toString(request.getParameter("num"), "").trim();
        if(numRaw.isEmpty()) {
            request.setAttribute("msgError", "Номер дома не указан");
            return false;
        }
        int num = 0;
        try {
            num = Integer.parseInt(numRaw);
        } catch(NumberFormatException e) {
            request.setAttribute("msgError", "Пожалуйста, указывайте номер дома цифрами");
            return false;
        }
        a.setNum(num);
        
        String subNumRaw = Objects.toString(request.getParameter("subnum"), "").trim();
        int subnum = 0;
        try {
            subnum = Integer.parseInt(subNumRaw);
        } catch(NumberFormatException e) {
            subnum = -1;
        }
        a.setSubnum(subnum);
        
        String flatRaw = Objects.toString(request.getParameter("flat"), "").trim();
        int flat = 0;
        try {
            flat = Integer.parseInt(flatRaw);
        } catch(NumberFormatException e) {
            flat = -1;
        }
        a.setFlat(flat);

        String extra = Objects.toString(request.getParameter("extra"), "").trim();
        a.setExtra(extra);
        return true;
        
    }
    
    
    
}

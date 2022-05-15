package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "Create", urlPatterns = {"/create"})
public class Create extends HttpServlet {
    
    private List<Client> clients;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {              
        response.setContentType("text/html;charset=UTF-8");
        
        this.clients = Client.listOfClients;
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Create</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>СОЗДАНИЕ НОВОЙ ЗАПИСИ</h1>\n");
            out.println("<p>Создать нового клиента и новый адрес</p>\n");
            out.println("<form action =\"create\" method=\"GET\">\n");            
            out.println("<p>Ввод данных о клиенте:</p>\n");
            out.println("<label for=\"type\">Тип</label><input type=\"text\" name=\"type\" required/><br>\n");
            out.println("<label for=\"model\">Модель</label><input type=\"text\" name=\"model\" required/><br>\n");
            out.println("<label for=\"ip\">IP</label><input type=\"text\" name=\"ip\" required/><br> \n");
            out.println("<p>Ввод данных об адресе:</p>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\" required/><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\" required/><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\" required/><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\"/><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\"/><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\"/><br>\n");
            out.println("<input type=\"submit\" value=\"CREATE\"/> \n");
            out.println("</form>");
            
            
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // process address
        if(createClient(request)) {
            response.sendRedirect("http://localhost:26213/J200_homework/viewlist");
        } else {
            //processRequest(request, response);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error");
            dispatcher.forward(request, response);
        }   
        
        
        //
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

    private boolean createClient(HttpServletRequest request) {
        int cId = 0;
        for(Client c : Client.listOfClients) {
            cId = cId >= c.getIdClient() ? cId : c.getIdClient();
        }
        
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
//        char[] typeCh = type.toCharArray();
//        for(char ch : typeCh) {
//            if(!Character.UnicodeBlock.of(ch).equals(Character.UnicodeBlock.BASIC_LATIN)|| !Character.isDigit(ch)) {
//                request.setAttribute("msgError", "В поле ТИП допустимы только латинские символы и цифры");
//                return false;
//            }
//        }
        
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
        
        Client c = new Client(cId + 1, type, model, ip);
        
        int aId = 0;        
        String city = Objects.toString(request.getParameter("city"), "").trim();
        if(city.isEmpty()) {
            request.setAttribute("msgError", "Город не указан");
            return false;
        }
        
        String street = Objects.toString(request.getParameter("street"), "").trim();
        if(street.isEmpty()) {
            request.setAttribute("msgError", "Улица не указана");
            return false;
        }
        
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
        
        String subNumRaw = Objects.toString(request.getParameter("subnum"), "").trim();
        int subnum = 0;
        try {
            subnum = Integer.parseInt(subNumRaw);
        } catch(NumberFormatException e) {
            subnum = -1;
        }
        
        String flatRaw = Objects.toString(request.getParameter("flat"), "").trim();
        int flat = 0;
        try {
            flat = Integer.parseInt(flatRaw);
        } catch(NumberFormatException e) {
            flat = -1;
        }

        String extra = Objects.toString(request.getParameter("extra"), "").trim();
        
        Address address = new Address(aId + 1, city, street, num, subnum, flat, extra);        
        c.addAddress(address);
        
        Client.listOfClients.add(c);
        
        return true;
    }
}

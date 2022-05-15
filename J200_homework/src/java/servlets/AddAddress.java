/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@WebServlet(name = "AddAddress", urlPatterns = {"/addaddress"})
public class AddAddress extends HttpServlet {

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
            out.println("<title>Добавить адрес</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Добавить адрес клиенту</p>\n");
            out.println("<form action =\"addaddress\" method=\"GET\">\n");
            out.println("<p>Ввод данных об адресе:</p>\n");
            out.println("<select name=\"clientToAddAddress\" required>");
            for (Client c : clients) {
                out.println("<option value=\"" + c.getIdClient() + "\">" + c.getModel() + " " + c.getIp() + "</option>");
            }
            out.println("</select>\n");
            out.println("<br>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\" required/><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\" required/><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\" required/><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\"/><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\"/><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\"/><br>\n");
            out.println("<input type=\"submit\" value=\"ADD ADDRESS\"/> \n");

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
        if (processAddressAndAdd(request)) {
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

    private boolean processAddressAndAdd(HttpServletRequest request) {
        
        // Most of parameters are checked in form. But, GET request can be written in the address field manually.
        
        int clientId = 0;
        String clientIdRaw = Objects.toString(request.getParameter("clientToAddAddress"), "");        
        if (clientIdRaw.length() > 0) {
            clientId = Integer.parseInt(clientIdRaw);
        } else {
            request.setAttribute("msgError", "Не выбран клиент");
            return false;
        }
        

        Client c = Client.getById(clientId);
        if(c == null) {
            request.setAttribute("msgError", "Клиент не найден");
            return false;
        }

        int id = 0;
        for (Address a : c.getAddresses()) {
            id = id >= a.getIdAddress() ? id : a.getIdAddress();
        }
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

        Address address = new Address(id + 1, city, street, num, subnum, flat, extra);
        c.addAddress(address);

        return true;
    }
}

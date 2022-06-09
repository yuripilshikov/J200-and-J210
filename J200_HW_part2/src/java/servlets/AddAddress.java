/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.UpdateBeanLocal;
import beans.repo.DbManagerLocal;
import entity.Address;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Client;
import java.util.Objects;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "AddAddress", urlPatterns = {"/addaddress2"})
public class AddAddress extends HttpServlet {
    
    @EJB
    UpdateBeanLocal updateLB;
    
    @EJB
    DbManagerLocal dbm;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        List<Address> addresses = dbm.getAllAddresses();
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Добавить адрес</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Добавить адрес клиенту</p>\n");
            out.println("<form action =\"addaddress2\" method=\"GET\">\n");
            out.println("<p>Ввод данных об адресе:</p>\n");
            out.println("<select name=\"clientToAddAddress\" required>");
            for (Address a : addresses) {
                out.println("<option value=\"" + a.getDevice().getIdclient()+ "\">" +a.getDevice().getModel() + " " + a.getDevice().getType() + " " + a.getDevice().getIp() + "</option>");
            }
            out.println("</select>\n");
            out.println("<br>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\"   value=\"" + Objects.toString(request.getParameter("city"),"") + "\"  required/><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("cityValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\"  value=\"" + Objects.toString(request.getParameter("street"),"") + "\"  required/><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("streetValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\"   value=\"" + Objects.toString(request.getParameter("num"),"") + "\"  required/><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("numValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\"   value=\"" + Objects.toString(request.getParameter("subnum"),"") + "\"  /><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\"   value=\"" + Objects.toString(request.getParameter("flat"),"") + "\"  /><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\"   value=\"" + Objects.toString(request.getParameter("extra"),"") + "\"  /><br>\n");
//            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\" required/><br>\n");
//            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\" required/><br>\n");
//            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\" required/><br>\n");
//            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\"/><br>\n");
//            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\"/><br>\n");
//            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\"/><br>\n");
            out.println("<input type=\"submit\" value=\"ADD ADDRESS\"/> \n");
            out.println("</form>");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\"/><br>");
            out.println("<a href=\"http://localhost:26213/J200_HW_part2/\">Перейти к списку</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        request.setCharacterEncoding("UTF-8");
        if (updateLB.addAddress(request)) {
            response.sendRedirect("http://localhost:26213/J200_HW_part2/");
        } else {
            processRequest(request, response);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/error2");
//            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }
}

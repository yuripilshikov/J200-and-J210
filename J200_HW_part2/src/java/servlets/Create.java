package servlets;

import beans.UpdateBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "Create", urlPatterns = {"/create2"})
public class Create extends HttpServlet {
    
    @EJB
    UpdateBeanLocal updateLB;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {              
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Create</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>СОЗДАНИЕ НОВОЙ ЗАПИСИ</h1>\n");
            out.println("<p>Создать нового клиента и новый адрес</p>\n");
            out.println("<form action =\"create2\" method=\"GET\">\n");            
            out.println("<p>Ввод данных о клиенте:</p>\n");
            out.println("<label for=\"type\">Тип</label><input type=\"text\" name=\"type\" value=\"" + Objects.toString(request.getParameter("type"),"") + "\" required></input><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("typeValErrMsg"),"")+"</p>\n");            
            
            out.println("<label for=\"model\">Модель</label><input type=\"text\" name=\"model\" value=\"" + Objects.toString(request.getParameter("model"),"") + "\" required/>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("modelValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"ip\">IP</label><input type=\"text\" name=\"ip\"  value=\"" + Objects.toString(request.getParameter("ip"),"") + "\" required/><br> \n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("ipValErrMsg"),"")+"</p><br>\n");
            out.println("<p>Ввод данных об адресе:</p>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\"   value=\"" + Objects.toString(request.getParameter("city"),"") + "\"  required/><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("cityValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\"  value=\"" + Objects.toString(request.getParameter("street"),"") + "\"  required/><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("streetValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\"   value=\"" + Objects.toString(request.getParameter("num"),"") + "\"  required/><br>\n");
            out.println("<p style=\"color:red\">"+Objects.toString(request.getAttribute("numValErrMsg"),"")+"</p><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\"   value=\"" + Objects.toString(request.getParameter("subnum"),"") + "\"  /><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\"   value=\"" + Objects.toString(request.getParameter("flat"),"") + "\"  /><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\"   value=\"" + Objects.toString(request.getParameter("extra"),"") + "\"  /><br>\n");
            out.println("<input type=\"submit\" value=\"CREATE\"/> \n");
            out.println("</form>");
            out.println("<br>");
            out.println("<br>");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\"/><br>");
            out.println("<a href=\"http://localhost:26213/J200_HW_part2/viewlist2\">Перейти к списку</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        if(updateLB.addEntry(request)) {
            response.sendRedirect("http://localhost:26213/J200_HW_part2/viewlist2");
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

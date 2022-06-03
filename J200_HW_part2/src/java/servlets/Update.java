package servlets;

import beans.UpdateBeanLocal;
import beans.repo.DbManagerLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Address;
import entity.Client;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "Update", urlPatterns = {"/update2"})
public class Update extends HttpServlet {
    
    @EJB
    UpdateBeanLocal updateLB;
    
    @EJB
    DbManagerLocal dbm;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int cid = 0;
        try {
            cid = Integer.parseInt(request.getParameter("cid"));
        } catch (NumberFormatException e) {
            response.sendRedirect("http://localhost:26213/J200_HW_part2/viewlist2");
        }
        
        Client c = dbm.getClientByID(cid);
        //Client c = Client.getById(cid);
        
        int aid = 0;
        try {
            aid = Integer.parseInt(request.getParameter("aid"));
        } catch (NumberFormatException e) {
            response.sendRedirect("http://localhost:26213/J200_HW_part2/viewlist2");
        }
        
        //Address a = c.getAddressById(aid);
        Address a = dbm.getAddressByID(aid);
                
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Редактирование записи</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>РЕДАКТИРОВАНИЕ ЗАПИСИ</h1>\n");
            out.println("<form action =\"update2\" method=\"POST\">\n");            
            out.println("<p>Данные клиента:</p>\n");
            
            
            out.println("<input type=\"hidden\" name=\"cliId\"  value=\" " + c.getIdclient() + " \" /><br>\n");
            out.println("<label for=\"type\">Тип</label><input type=\"text\" name=\"type\" required value=\"" + c.getType() + "\" /><br>\n");
            out.println("<label for=\"model\">Модель</label><input type=\"text\" name=\"model\" required value=\"" + c.getModel()+ "\" /><br>\n");
            out.println("<label for=\"ip\">IP</label><input type=\"text\" name=\"ip\" required value=\"" + c.getIp()+ "\" /><br> \n");
            out.println("<p>Данные адреса:</p>\n");
            out.println("<input type=\"hidden\" name=\"addrId\"  value=\"" + a.getIdaddress() + "\" /><br>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\" required value=\"" + a.getCity() + "\" /><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\" required value=\" " + a.getStreet() + " \" /><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\" required value=\"" + a.getNum() + "\" /><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\" value=\"" + a.getSubnum() + "\" /><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\" value=\"" + a.getFlat() + "\" /><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\" value=\"" + a.getExtra() + "\" /><br>\n");
            out.println("<input type=\"submit\" value=\"ВНЕСТИ ИЗМЕНЕНИЯ\"/> \n");
            out.println("</form>");
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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // set new values
        if(updateLB.updateEntry(request)) {
            response.sendRedirect("http://localhost:26213/J200_HW_part2/viewlist2");
        } else {
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/error2");
//            dispatcher.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

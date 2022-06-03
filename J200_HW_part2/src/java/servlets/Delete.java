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
@WebServlet(name = "Delete", urlPatterns = {"/delete2"})
public class Delete extends HttpServlet {
    
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
        //Client c = Client.getById(cid);
        Client c = dbm.getClientByID(cid);
        
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
            out.println("<title>Удаление</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>РЕДАКТИРОВАНИЕ ЗАПИСИ</h1>\n");
            out.println("<form action =\"delete2\" method=\"POST\">\n");            
            out.println("<p>Будет удалён следующий адрес:</p>\n");
            out.println("<p>" + a.toString() + "</p>\n");
            out.println("<p>у следующего клиента:</p>\n");
            out.println("<p>" + c.toString() + "</p>\n");            
            if(c.getAddressList().size() == 1) {
                out.println("<p style=\"color:red\">сам клиент также будет удалён</p>\n");
            }           
            out.println("<p>ПОДТВЕРДИТЕ УДАЛЕНИЕ</p>\n");
            out.println("<input type=\"hidden\" name=\"cliId\"  value=\"" + c.getIdclient() + "\" /><br>\n");            
            out.println("<input type=\"hidden\" name=\"addrId\"  value=\"" + a.getIdaddress() + "\" /><br>\n");            
            out.println("<input type=\"submit\" value=\"УДАЛИТЬ\"/> \n");
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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        if(updateLB.deleteEntry(request)) {
            response.sendRedirect("http://localhost:26213/J200_HW_part2/viewlist2");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error2");
            dispatcher.forward(request, response);
        }        
    }

}

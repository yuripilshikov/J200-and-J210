package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Delete", urlPatterns = {"/delete"})
public class Delete extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Удаление</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>РЕДАКТИРОВАНИЕ ЗАПИСИ</h1>\n");
            out.println("<form action =\"delete\" method=\"POST\">\n");            
            out.println("<p>Будет удалён следующий адрес:</p>\n");
            out.println("<p>" + a.toString() + "</p>\n");
            out.println("<p>у следующего клиента:</p>\n");
            out.println("<p>" + c.toString() + "</p>\n");            
            if(c.getAddresses().size() == 1) {
                out.println("<p style=\"color:red\">сам клиент также будет удалён</p>\n");
            }           
            out.println("<p>ПОДТВЕРДИТЕ УДАЛЕНИЕ</p>\n");
            out.println("<input type=\"hidden\" name=\"cliId\"  value=\"" + c.getIdClient() + "\" /><br>\n");            
            out.println("<input type=\"hidden\" name=\"addrId\"  value=\"" + a.getIdAddress() + "\" /><br>\n");            
            out.println("<input type=\"submit\" value=\"УДАЛИТЬ\"/> \n");
            out.println("</form>");
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
        
        if(delete(request)) {
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

    private boolean delete(HttpServletRequest request) {
        int cid = 0;
        try {
            cid = Integer.parseInt(request.getParameter("cliId"));
        } catch (NumberFormatException e) {
            request.setAttribute("msgError", "Не могу получить ID клиента");
            return false;
        }        
        Client c = Client.getById(cid);
        
        int aid = 0;
        try {
            aid = Integer.parseInt(request.getParameter("addrId"));
        } catch (NumberFormatException e) {
            request.setAttribute("msgError", "Не могу получить ID адреса");
            return false;
        }        
        Address a = c.getAddressById(aid);
        
        c.getAddresses().remove(a);
        if(c.getAddresses().isEmpty()) {
            Client.listOfClients.remove(c);
        }
        return true;
    }
}

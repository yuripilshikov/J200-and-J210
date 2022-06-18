package servlets;

import beans.SelectBeanLocal;
import entity.Address;
import entity.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dom.DemoDOM;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "CheckDOM", urlPatterns = {"/CheckDOM"})
public class CheckDOM extends HttpServlet {

    @EJB
    SelectBeanLocal selectLocal;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String filterName = request.getParameter("modelText");
        List<Client> clients = selectLocal.getFliteredClients(filterName);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckDOM</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Результаты поиска:</h1>");
            out.println("<p>Найдено " + clients.size() + " клиентов, подходящих под параметры поиска (передано: \" " + filterName + " \")</p><br>");

            if (clients != null && clients.size() > 0) {
                out.println("<ol>");
                for (Client c : clients) {
                    out.println("<li>" + c.getModel() + " " + c.getType() + " " + c.getIp() + "</li>");

                    List<Address> addresses = c.getTempAddressList();
                    if (addresses != null && addresses.size() > 0) {
                        out.println("<ul>");
                        for (Address a : addresses) {
                            out.println("<li>" + a.getIdaddress() + " " + a.getCity() + " " + a.getStreet() + "</li>");
                        }
                        out.println("</ul>");
                    }
                }
                out.println("</ol>");
            } else {
                out.println("<p>No client found!</p>");
            }
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\"/><br>");
            out.println("<a href=\"http://localhost:26213/J200_HW_part2/\">Перейти к списку</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

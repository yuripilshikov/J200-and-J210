package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author YuriPilshikov
 */
@WebServlet(name = "Create", urlPatterns = {"/create"})
public class Create extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Create</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>СОЗДАНИЕ ЗАПИСИ</h1>\n");
            out.println("<p>Создать нового клиента и новый адрес</p>\n");
            out.println("<form action =\"create\" method=\"GET\">\n");
            out.println("<p>Ввод данных об адресе:</p>\n");
            out.println("<label for=\"city\">Город</label><input type=\"text\" name=\"city\"/><br>\n");
            out.println("<label for=\"street\">Улица</label><input type=\"text\" name=\"street\"/><br>\n");
            out.println("<label for=\"num\">Дом</label><input type=\"number\" name=\"num\"/><br>\n");
            out.println("<label for=\"subnum\">Корпус</label><input type=\"number\" name=\"subnum\"/><br>\n");
            out.println("<label for=\"flat\">Квартира</label><input type=\"number\" name=\"flat\"/><br>\n");
            out.println("<label for=\"extra\">Дополнительно</label><input type=\"text\" name=\"extra\"/><br>\n");
            out.println("<p>Ввод данных о клиенте:</p>\n");
            out.println("<label for=\"type\">Тип</label><input type=\"text\" name=\"type\"/><br>\n");
            out.println("<label for=\"model\">Модель</label><input type=\"text\" name=\"model\"/><br>\n");
            out.println("<label for=\"ip\">IP</label><input type=\"text\" name=\"ip\"/><br> \n");
            out.println("<input type=\"submit\" value=\"CREATE\"/> \n");
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

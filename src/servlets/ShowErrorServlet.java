package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ShowErrorServlet")
public class ShowErrorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        displayErrorPage(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        displayErrorPage(req, resp);
    }

    public void displayErrorPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<form method='GET' action='" + resp.encodeURL(req.getContextPath() + "/LoginServlet") + "'>");
        out.println("</p>");
        out.println("<p>error</p>");
        out.println("<input type='submit' name='return' value='return'>");
        out.println("</form>");
        out.println("</body></html>");
    }
}

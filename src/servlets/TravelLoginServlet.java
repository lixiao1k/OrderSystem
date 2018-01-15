package servlets;

import listeners.ContextDataHelper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//游客登陆的Servlet

@WebServlet("/TravelLoginServlet")
public class TravelLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ContextDataHelper contextDataHelper = new ContextDataHelper();
        HttpSession session = req.getSession(false);
        int users, travellers;

        if(null != req.getParameter("travellers") && null == session){//判断是游客登陆并且是首次登陆
            session = req.getSession(true);
            session.setMaxInactiveInterval(1*60);
            contextDataHelper.plusOne(getServletContext(), "travellers");
            session.setAttribute("travellers", req.getRemoteAddr());
            System.out.println("Traveller Created");
        }

        users = contextDataHelper.getData(getServletContext(), "users");
        travellers = contextDataHelper.getData(getServletContext(), "travellers");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("</p>Welcome to my website!");
        out.println("</p>在线总人数: " + (users + travellers));
        out.println("</p>已登陆人数: " + users);
        out.println("</p>游客人数: " + travellers);
        out.println("</body></html>");
    }


}

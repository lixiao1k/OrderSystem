package servlets;

import listeners.ContextCounterListener;
import listeners.ContextDataHelper;
import listeners.SessionCounterListener;

import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/TravelLoginServlet")
public class TravelLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ContextDataHelper contextDataHelper = new ContextDataHelper();
        HttpSession session = req.getSession(false);
        int counter, travellers;

        if(null != req.getParameter("travellers") && null == session){
            session = req.getSession(true);
            session.setMaxInactiveInterval(1*60);
            contextDataHelper.plusOne(getServletContext(), "travellers");
            session.setAttribute("travellers", req.getRemoteAddr());
        }

        counter = contextDataHelper.getData(getServletContext(), "webCounter");
        travellers = contextDataHelper.getData(getServletContext(), "travellers");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("</p>Welcome to my website!");
        out.println("</p>在线总人数: " + counter);
        out.println("</p>已登陆人数: " + (counter - travellers));
        out.println("</p>游客人数: " + travellers);
        out.println("</body></html>");
    }

//    private int addAndGetCounter(HttpServletRequest req){
//        ServletContext context = req.getServletContext();
//        Integer counter = Integer.parseInt((String) context.getAttribute("webCounter"));
//        counter++;
//        context.setAttribute("webCounter", Integer.toString(counter));
//        return counter;
//    }

}

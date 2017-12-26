package servlets;

import listeners.SessionCounterListener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet(){
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println(req.getRemoteAddr());

        String login = "";
        HttpSession session = req.getSession(false);
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();

        if(null != cookies){
            for(int i = 0; i < cookies.length; i++){
                cookie = cookies[i];
                if(cookie.getName().equals("LoginCookie")){
                    login = cookie.getValue();
                    break;
                }
            }
        }


        if (null != req.getParameter("Logout")){
            if(null != session) {
                session.invalidate();
                SessionCounterListener listener = new SessionCounterListener();
                session = null;
            }
        }

        ServletContext context = getServletContext();
        int webCounter = Integer.parseInt((String) context.getAttribute("webCounter"));
        int travellers = Integer.parseInt((String) context.getAttribute("travellers"));


        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        out.println("<form method='POST' action='"
                        + resp.encodeURL(req.getContextPath()+"/ShowOrdersServlet")
                        + "'>");
        out.println("login: <input type='text' name='login' value='" + login + "'>");
        out.println("password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Submit' value='login'>");
        out.println("</p>在线总人数: " + webCounter);
        out.println("</p>已登陆人数: " + (webCounter - travellers));
        out.println("</p>游客人数: " + travellers);
        out.println("</form>");
        out.println("<form method='POST' action='"
                + resp.encodeURL(req.getContextPath()+"/TravelLoginServlet")
                + "'>");
        out.println("<input type='submit' name='travellers' value='traveller access'>");
        out.println("</form></body></html>");

    }

}

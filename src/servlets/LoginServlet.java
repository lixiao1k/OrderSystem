package servlets;

import listeners.SessionCounterListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet(){
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        ServletContext context = getServletContext();
        int webCounter = Integer.parseInt((String) context.getAttribute("webCounter"));




        if (null != req.getParameter("Logout")){
            if(null != session) {
                session.invalidate();
                SessionCounterListener listener = new SessionCounterListener();
                session = null;
            }
        }else {
            webCounter++;
            context.setAttribute("webCounter", Integer.toString(webCounter));
        }

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        out.println("<form method='POST' action='"
                        + resp.encodeURL(req.getContextPath()+"/ShowOrdersServlet")
                        + "'>");
        out.println("login: <input type='text' name='login' value='" + login + "'>");
        out.println("password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Submit' value='Submit'>");
        out.println("</p>You are visitor number " + webCounter);
        out.println("</form></body></html>");

    }

}

package listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionCounterListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created");
        System.out.println(se.getSession().getAttribute("Login"));
        ServletContext context = se.getSession().getServletContext();
        Integer counter = Integer.parseInt((String) context.getAttribute("webCounter"));
        counter++;
        context.setAttribute("webCounter", Integer.toString(counter));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("destroyed");
        ServletContext context = se.getSession().getServletContext();
        Integer counter = Integer.parseInt((String) context.getAttribute("webCounter"));
        counter--;
        context.setAttribute("webCounter", Integer.toString(counter));
    }
}

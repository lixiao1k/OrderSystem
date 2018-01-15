package listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionCounterListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) { //根据session中的属性判断是user还是traveller的session
        ContextDataHelper contextDataHelper = new ContextDataHelper();
        ServletContext context = se.getSession().getServletContext();
        if(null != se.getSession().getAttribute("login")){
            contextDataHelper.minusOne(context, "users");
            System.out.println("user destroyed");
        }else if(null != se.getSession().getAttribute("travellers")){
            contextDataHelper.minusOne(context, "travellers");
            System.out.println("traveller destroyed");
        }else {
            System.out.println("error");
        }
    }

}

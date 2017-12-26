package listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Hashtable;

@WebListener
public class SessionCounterListener implements HttpSessionListener{

    private static Hashtable userSessionList = new Hashtable();
    private static Hashtable travellerSessionList = new Hashtable();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("destroyed");
        ContextDataHelper contextDataHelper = new ContextDataHelper();
        ServletContext context = se.getSession().getServletContext();
        if(null != se.getSession().getAttribute("login")){
            contextDataHelper.minusOne(context, "webCounter");
        }else if(null != se.getSession().getAttribute("travellers")){
            contextDataHelper.minusOne(context, "travellers");
        }else {
            System.out.println("error");
        }
    }

    public static int getUserNum(){
        return userSessionList.size();
    }
    public static int getTravellerNum(){
        return travellerSessionList.size();
    }

}

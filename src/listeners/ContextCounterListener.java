package listeners;

import action.bussiness.UserCountBean;
import factory.DaoFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.*;

@WebListener
public class ContextCounterListener implements ServletContextListener, ServletContextAttributeListener{

    public ContextCounterListener(){

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        int users = DaoFactory.getUserDao().getUserCount();
        int travellers = DaoFactory.getUserDao().getTravellerCount();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("users", Integer.toString(users));
        servletContext.setAttribute("travellers", Integer.toString(travellers));
    }


    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {

    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        DaoFactory.getUserDao().writeCount(scae);
    }


}

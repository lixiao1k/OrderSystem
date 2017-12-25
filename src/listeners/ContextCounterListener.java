package listeners;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.*;

@WebListener
public class ContextCounterListener implements ServletContextListener, ServletContextAttributeListener{
    private int counter;
    String counterFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/counter.txt";

    public ContextCounterListener(){

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(counterFilePath));
            counter = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("webCounter", Integer.toString(counter));
    }


    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {

    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {

        writeCounter(scae);


    }


    synchronized void writeCounter(ServletContextAttributeEvent scae){
        ServletContext servletContext = scae.getServletContext();
        counter = Integer.parseInt((String) servletContext.getAttribute("webCounter"));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
            writer.write(Integer.toString(counter));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

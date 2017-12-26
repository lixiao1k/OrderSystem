package listeners;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.*;

@WebListener
public class ContextCounterListener implements ServletContextListener, ServletContextAttributeListener{
    private int counter;
    private int travellers;
    String counterFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/counter.txt";
    String travellerFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/traveller.txt";
    public ContextCounterListener(){

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        counter = getData(counterFilePath);
        travellers = getData(travellerFilePath);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("webCounter", Integer.toString(counter));
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
        writeCounter(scae);
    }

    private int getData(String path){
        int data = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            data = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    synchronized void writeCounter(ServletContextAttributeEvent scae){
        ServletContext servletContext = scae.getServletContext();
        counter = Integer.parseInt((String) servletContext.getAttribute("webCounter"));
        travellers = Integer.parseInt((String) servletContext.getAttribute("travellers"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
            writer.write(Integer.toString(counter));
            writer.close();
            writer = new BufferedWriter(new FileWriter(travellerFilePath));
            writer.write(Integer.toString(travellers));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

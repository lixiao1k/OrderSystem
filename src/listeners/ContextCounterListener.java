package listeners;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.*;

@WebListener
public class ContextCounterListener implements ServletContextListener, ServletContextAttributeListener{
    private int travellers;
    private int users;

    String travellerFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/traveller.txt";
    String userFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/user.txt";

    public ContextCounterListener(){

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        users = getData(userFilePath);
        travellers = getData(travellerFilePath);
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

    synchronized void writeCounter(ServletContextAttributeEvent scae){//不管是对那类用户属性的变更操作，两类用户登陆数据同时更新
        ServletContext servletContext = scae.getServletContext();
        users = Integer.parseInt((String) servletContext.getAttribute("users"));
        travellers = Integer.parseInt((String) servletContext.getAttribute("travellers"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath));
            writer.write(Integer.toString(users));
            writer.close();
            writer = new BufferedWriter(new FileWriter(travellerFilePath));
            writer.write(Integer.toString(travellers));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

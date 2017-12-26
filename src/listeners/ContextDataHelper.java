package listeners;

import javax.jws.Oneway;
import javax.servlet.ServletContext;

public class ContextDataHelper {

    public int getData(ServletContext context, String attribute){
        if(null != context.getAttribute(attribute)){
            Integer data = Integer.parseInt((String) context.getAttribute(attribute));
            return data;
        }else {
            return -1;
        }
    }


    public void plusOne(ServletContext context, String attribute){
        if(null != context.getAttribute(attribute)){
            Integer data = Integer.parseInt((String) context.getAttribute(attribute));
            data++;
            context.setAttribute(attribute, Integer.toString(data));
        }else {
            context.setAttribute(attribute, Integer.toString(1));
        }
    }

    public void minusOne(ServletContext context, String attribute){
        if (null != context.getAttribute(attribute)){
            Integer data = Integer.parseInt((String) context.getAttribute(attribute));
            data--;
            context.setAttribute(attribute, Integer.toString(data));
        }else {
            System.out.println("error");
        }
    }
}

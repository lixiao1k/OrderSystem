package tag;

import action.bussiness.OrderListBean;
import netscape.javascript.JSException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class OrderInfoHandler extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {

        try {
            OrderListBean listOrder = (OrderListBean) getJspContext().findAttribute("listOrder");
            JspWriter out = getJspContext().getOut();
            for(int i = 0; i < listOrder.getOrderList().size(); i++){
                out.println("<tr><TD align='center'>"
                        + listOrder.getOrderList(i).getOrderid() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getUserid() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getOrdername() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getAmount() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getPrice()+ "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getDate()+ "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getOos() + "</TD></tr>");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new JSException(e.getMessage());
        }

    }

}

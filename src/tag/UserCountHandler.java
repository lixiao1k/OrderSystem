package tag;

import action.bussiness.UserCountBean;
import netscape.javascript.JSException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class UserCountHandler extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        try {
            UserCountBean userCount = (UserCountBean) getJspContext().findAttribute("userCount");
            JspWriter out = getJspContext().getOut();
            out.println("</p>在线总人数: " + (userCount.getUserCount() + userCount.getTravellerCount()));
            out.println("</p>已登陆人数: " + userCount.getUserCount());
            out.println("</p>游客人数: " + userCount.getTravellerCount());

        }catch (Exception e){
            e.printStackTrace();
            throw new JSException(e.getMessage());
        }
    }
}

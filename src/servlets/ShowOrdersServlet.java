package servlets;

import com.sun.tools.corba.se.idl.constExpr.Or;
import model.Order;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet("/ShowOrdersServlet")
public class ShowOrdersServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;

    public ShowOrdersServlet(){
        super();
    }

    public void init(){
        InitialContext jndiContext = null;
        Properties properties = new Properties();
        properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
        properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        try {
            jndiContext = new InitialContext(properties);
            datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/ordersystem");
            System.out.println("got context");
            System.out.println("About to get ds---ShowMyStock");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        boolean cookieFound = false;
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();
        if(null != cookies){
            for(int i = 0; i < cookies.length; i++){
                cookie = cookies[i];
                if(cookie.getName().equals("LoginCookie")){
                    cookieFound = true;
                    break;
                }
            }
        }

        String loginValue = req.getParameter("login");
        boolean isLoginAction = (null == loginValue) ? false : true;
        if(isLoginAction){
            String password = req.getParameter("password");
            if(isUser(loginValue, password)){
                if(cookieFound){
                    if(!loginValue.equals(cookie.getValue())){
                        cookie.setValue(loginValue);
                        resp.addCookie(cookie);
                    }
                }else{
                    cookie = new Cookie("LoginCookie", loginValue);
                    cookie.setMaxAge(Integer.MAX_VALUE);
                    resp.addCookie(cookie);
                }
                if(null == session){
                    session = req.getSession(true);
                    session.setAttribute("login", loginValue);
                }
                req.setAttribute("login", loginValue);
                getOrdersList(req, resp);
                displayOrdersTable(req, resp);
                displayLogoutPage(req, resp);
            }else{
                resp.sendRedirect(req.getContextPath() + "/ShowErrorServlet");
            }

        }else{
            if(null == session){
                resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            }else{
                req.setAttribute("login", loginValue);
                displayLogoutPage(req, resp);
            }
        }
    }

    private boolean isUser(String userid, String password){
        boolean isUser = false;
        String passwd = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt = connection.prepareStatement("SELECT * FROM user WHERE userid = ?");
            stmt.setInt(1,Integer.parseInt(userid));
            result = stmt.executeQuery();
            while (result.next()){
                passwd = result.getString("passwd");
            }
            if(passwd == null){
                isUser = false;
            }else if(passwd.equals(password)){
                isUser = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeSource(result, stmt, connection);
        }

        return isUser;
    }

    public void getOrdersList(HttpServletRequest req, HttpServletResponse res){
        int userid = Integer.parseInt(req.getParameter("login"));
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList list = new ArrayList();

        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt = connection.prepareStatement("SELECT * from orders WHERE userid = ?");
            stmt.setInt(1, userid);
            result = stmt.executeQuery();
            while (result.next()){
                Order order = new Order();
                order.setOrderid(result.getInt(1));
                order.setUserid(result.getInt(2));
                order.setOrdername(result.getString(3));
                order.setAmount(result.getInt(4));
                order.setPrice(result.getDouble(5));
                order.setDate(result.getDate(6));
                order.setOos(result.getString(7).charAt(0));
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeSource(result, stmt, connection);

        }
        req.setAttribute("list", list);
    }

    private void closeSource(ResultSet result, PreparedStatement stmt, Connection connection ){
        try {
            if(null != result){
                result.close();
            }
            if(null != stmt){
                stmt.close();
            }
            if(null != connection){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayOrdersTable(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ArrayList list = (ArrayList) req.getAttribute("list");

        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        out.println("<table width='650' border='0' >");
        out.println("<tr>");
        out.println("<th>order_id</th>");
        out.println("<th>order_name</th>");
        out.println("<th>amount</th>");
        out.println("<th>price</th>");
        out.println("<th>time</th>");
        out.println("<th>oos</th>");
        out.println("</tr>");
        for(int i = 0; i < list.size(); i++){
            Order order = (Order) list.get(i);
            out.println("<tr>");
            out.println("<td>" + order.getOrderid() + "</td>");
            out.println("<td>" + order.getOrdername() + "</td>");
            out.println("<td>" + order.getAmount() + "</td>");
            out.println("<td>" + order.getPrice() + "</td>");
            out.println("<td>" + order.getDate() + "</td>");
            out.println("<td>" + order.getOos() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

    }

    public void displayLogoutPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        // 注销Logout
//        out.println("<html><body>");
        out.println("<form method='GET' action='" + res.encodeURL(req.getContextPath() + "/LoginServlet") + "'>");
        out.println("</p>");
        out.println("<input type='submit' name='Logout' value='Logout'>");
        out.println("</form>");
        out.println("</body></html>");
    }



}

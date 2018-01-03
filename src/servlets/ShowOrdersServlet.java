package servlets;

import action.bussiness.OrderListBean;
import action.bussiness.UserCountBean;
import factory.DaoFactory;
import factory.ServiceFactory;
import listeners.ContextDataHelper;
import model.Order;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
//    private DataSource datasource = null;

    public ShowOrdersServlet(){
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            if(DaoFactory.getUserDao().isUser(loginValue, password)){
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
                    System.out.println("User Created");
                    session.setMaxInactiveInterval(1*60);
                    session.setAttribute("login", loginValue);
                    ContextDataHelper contextDataHelper = new ContextDataHelper();
                    contextDataHelper.plusOne(getServletContext(), "users");
                }
                req.setAttribute("login", loginValue);
                getOrdersList(req, resp);
            }else{
                resp.sendRedirect(req.getContextPath() + "/ShowErrorServlet");
            }

        }else{
            if(null == session){
                resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            }else{
                req.setAttribute("login", loginValue);
                getOrdersList(req, resp);
            }
        }
    }

    public void getOrdersList(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(true);
        ServletContext context = getServletContext();

        OrderListBean listOrder = new OrderListBean();
        String userid = req.getParameter("login");
        listOrder.setOrderList(ServiceFactory.getOrderManageService().getMyOrder(userid));

        try {
            if(listOrder.getOrderList().size() < 1){
                context.getRequestDispatcher("/order/noListOrder.jsp").forward(req, res);
            } else {
                session.setAttribute("listOrder", listOrder);
                context.getRequestDispatcher("/order/listOrder.jsp").forward(req, res);
            }
        }catch (ServletException e){
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "This is a ServletException.");
        }
    }

//    public void displayOrdersTable(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        ArrayList list = (ArrayList) req.getAttribute("list");
//
//        PrintWriter out = res.getWriter();
//        out.println("<html><body>");
//        out.println("<table width='650' border='0' >");
//        out.println("<tr>");
//        out.println("<th>order_id</th>");
//        out.println("<th>order_name</th>");
//        out.println("<th>amount</th>");
//        out.println("<th>price</th>");
//        out.println("<th>time</th>");
//        out.println("<th>oos</th>");
//        out.println("</tr>");
//        for(int i = 0; i < list.size(); i++){
//            Order order = (Order) list.get(i);
//            out.println("<tr>");
//            out.println("<td>" + order.getOrderid() + "</td>");
//            out.println("<td>" + order.getOrdername() + "</td>");
//            out.println("<td>" + order.getAmount() + "</td>");
//            out.println("<td>" + order.getPrice() + "</td>");
//            out.println("<td>" + order.getDate() + "</td>");
//            out.println("<td>" + order.getOos() + "</td>");
//            out.println("</tr>");
//        }
//        out.println("</table>");
//
//    }

//    public void displayOosOrdersTable(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        ArrayList oosList = (ArrayList) req.getAttribute("oosList");
//
//        if(oosList.size() != 0){
//            PrintWriter out = resp.getWriter();
//            out.println("<p style=\"color:red\">Alert! The following orders are out of stock!</p>");
//            out.println("<table width='650' border='0' >");
//            out.println("<tr>");
//            out.println("<th style=\"color:red\">order_id</th>");
//            out.println("<th style=\"color:red\">order_name</th>");
//            out.println("<th style=\"color:red\">amount</th>");
//            out.println("<th style=\"color:red\">price</th>");
//            out.println("<th style=\"color:red\">time</th>");
//            out.println("<th style=\"color:red\">oos</th>");
//            out.println("</tr>");
//            for(int i = 0; i < oosList.size(); i++){
//                Order order = (Order) oosList.get(i);
//                out.println("<tr>");
//                out.println("<td style=\"color:red\">" + order.getOrderid() + "</td>");
//                out.println("<td style=\"color:red\">" + order.getOrdername() + "</td>");
//                out.println("<td style=\"color:red\">" + order.getAmount() + "</td>");
//                out.println("<td style=\"color:red\">" + order.getPrice() + "</td>");
//                out.println("<td style=\"color:red\">" + order.getDate() + "</td>");
//                out.println("<td style=\"color:red\">" + order.getOos() + "</td>");
//                out.println("</tr>");
//            }
//            out.println("</table>");
//        }
//    }

//    public void displayLogoutPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        PrintWriter out = res.getWriter();
//        // 注销Logout
////        out.println("<html><body>");
//        out.println("<form method='GET' action='" + res.encodeURL(req.getContextPath() + "/LoginServlet") + "'>");
//        out.println("</p>");
//        out.println("<input type='submit' name='Logout' value='Logout'>");
//        out.println("</form>");
//
//    }
//
//    public void displayCountPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        ContextDataHelper contextDataHelper = new ContextDataHelper();
//        int users = contextDataHelper.getData(getServletContext(), "users");
//        int travellers = contextDataHelper.getData(getServletContext(), "travellers");
//        PrintWriter out = res.getWriter();
//        out.println("</p>在线总人数: " + (users + travellers));
//        out.println("</p>已登陆人数: " + users);
//        out.println("</p>游客人数: " + travellers);
//        out.println("</body></html>");
//    }

}

package service.impl;

import factory.DaoFactory;
import model.Order;
import service.OrderManageService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderManageServiceImpl implements OrderManageService {

    private static OrderManageService orderManageService = new OrderManageServiceImpl();

    public static OrderManageService getInstance(){
        return orderManageService;
    }



    @Override
    public List getMyOrder(String userid) {
        ArrayList list1 = new ArrayList();
        list1 = (ArrayList) DaoFactory.getMyOrderDao().findOrderid(userid);
        ArrayList list2 = new ArrayList();

        for(int i = 0; i < list1.size(); i++){
            Order order = null;
            int ordid = (int) list1.get(i);
            order = DaoFactory.getOrderDao().find(ordid);
            list2.add(order);
        }
        return list2;
    }


    @Override
    public void sentErrorMessage(String message, HttpServletRequest req) throws ServletException, IOException {

        req.setAttribute("message", message);
    }

    @Override
    public void sentMessage(String message, HttpServletRequest req) throws ServletException, IOException {

        req.setAttribute("message", message);
    }

    @Override
    public void forwordPage(String page, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL(page));
        dispatcher.forward(req, resp);
    }
}

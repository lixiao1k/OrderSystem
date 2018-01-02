package factory;

import dao.DaoHelper;
import dao.MyOrderDao;
import dao.OrderDao;
import dao.UserDao;
import dao.impl.MyOrderDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.UserDaoImpl;

public class DaoFactory {

    public static OrderDao getOrderDao(){
        return OrderDaoImpl.getInstance();
    }

    public static MyOrderDao getMyOrderDao(){
        return MyOrderDaoImpl.getInstance();
    }

    public static UserDao getUserDao(){
        return UserDaoImpl.getInstance();
    }
}

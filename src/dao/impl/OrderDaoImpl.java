package dao.impl;

import dao.DaoHelper;
import dao.OrderDao;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao{

    private static OrderDaoImpl orderDao = new OrderDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    private OrderDaoImpl(){

    }

    public static OrderDaoImpl getInstance(){
        return orderDao;
    }

    @Override
    public Order find(int id) {
        Connection conn = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        Order order = new Order();
        try {
            stmt = conn.prepareStatement("SELECT * from orders WHERE ordid = ?");
            stmt.setInt(1,id);
            result = stmt.executeQuery();
            while (result.next()){
                order.setOrderid(result.getInt(1));
                order.setUserid(result.getInt(2));
                order.setOrdername(result.getString(3));
                order.setAmount(result.getInt(4));
                order.setPrice(result.getDouble(5));
                order.setDate(result.getDate(6));
                order.setOos(result.getString(7).charAt(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoHelper.closeConnection(conn);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        return order;
    }
}

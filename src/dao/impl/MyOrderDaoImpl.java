package dao.impl;

import dao.DaoHelper;
import dao.MyOrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyOrderDaoImpl implements MyOrderDao {
    private static MyOrderDaoImpl myOrderDao = new MyOrderDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    private MyOrderDaoImpl(){}

    public static MyOrderDaoImpl getInstance(){
        return myOrderDao;
    }

    @Override
    public List findOrderid(String userid) {

        Connection conn = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList list = new ArrayList();

        try {
            stmt = conn.prepareStatement("SELECT ordid from orders WHERE userid = ?");
            stmt.setInt(1, Integer.parseInt(userid));
            result = stmt.executeQuery();
            while (result.next()){
                list.add(result.getInt("ordid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoHelper.closeConnection(conn);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        return list;
    }
}

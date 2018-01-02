package dao.impl;

import dao.DaoHelper;
import dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl userDao = new UserDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();


    private UserDaoImpl(){

    }

    public static UserDaoImpl getInstance(){
        return userDao;
    }


    @Override
    public boolean isUser(String userid, String password) {
        boolean isUser = false;
        String passwd = null;
        Connection conn = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM user WHERE userid = ?");
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
        } finally {
            daoHelper.closeConnection(conn);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        return isUser;
    }
}

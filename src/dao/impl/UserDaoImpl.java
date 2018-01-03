package dao.impl;

import dao.DaoHelper;
import dao.UserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl userDao = new UserDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();
    private static int userCount;
    private static int travellercount;
    private static String travellerFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/traveller.txt";
    private static String userFilePath = "/Users/shelton/My/Code/Web-apps/OrderSystem/web/WEB-INF/user.txt";


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

    @Override
    public void setUserCount(int count) {
        userCount = count;
    }

    @Override
    public int getUserCount() {
        userCount = getData(userFilePath);
        return userCount;
    }

    @Override
    public void setTravellerCount(int count) {
        travellercount = count;
    }

    @Override
    public int getTravellerCount() {
        travellercount = getData(travellerFilePath);
        return travellercount;
    }

    @Override
    public void writeCount(ServletContextAttributeEvent scae) {
        write(scae);
    }


    public synchronized void write(ServletContextAttributeEvent scae){
        ServletContext servletContext = scae.getServletContext();
        int users = Integer.parseInt((String) servletContext.getAttribute("users"));
        int travellers = Integer.parseInt((String) servletContext.getAttribute("travellers"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath));
            writer.write(Integer.toString(users));
            writer.close();
            writer = new BufferedWriter(new FileWriter(travellerFilePath));
            writer.write(Integer.toString(travellers));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getData(String path){
        int data = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            data = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

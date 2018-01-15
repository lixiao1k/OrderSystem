package dao.impl;

import dao.BaseDao;
import dao.DaoHelper;
import dao.UserDao;
import model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static UserDaoImpl userDao = new UserDaoImpl();
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
        User user = findUserById(userid);
        if(user != null){
            if(user.getPasswd().equals(password)){
                isUser = true;
            }
        }
        return isUser;
    }


    @Override
    public int getUserCount() {
        userCount = getData(userFilePath);
        return userCount;
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

    @Override
    public void save(User user) {
        super.save(user);

    }

    @Override
    public void delete(User user) {
        super.save(user);
    }

    @Override
    public User findUserById(String userid) {
        User user = (User) super.load(User.class, Integer.parseInt(userid));
        return user;
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

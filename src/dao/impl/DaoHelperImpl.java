package dao.impl;

import dao.DaoHelper;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DaoHelperImpl implements DaoHelper{

    private static DaoHelperImpl baseDao = new DaoHelperImpl();

    private InitialContext jndiContext = null;
    private Connection connection = null;
    private DataSource dataSource = null;

    public DaoHelperImpl(){
        Properties properties = new Properties();
        properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
        properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        try {
            jndiContext = new InitialContext(properties);
            dataSource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/ordersystem");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("get context");
        System.out.println("About to get ds---DaoHelper");
    }

    public static DaoHelperImpl getBaseDaoInstance(){
        return baseDao;
    }

    @Override
    public Connection getConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public void closeConnection(Connection conn) {
        if(null != conn){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void closePreparedStatement(PreparedStatement stmt) {
        if(null != stmt){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void closeResult(ResultSet result) {
        if(null != result){
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

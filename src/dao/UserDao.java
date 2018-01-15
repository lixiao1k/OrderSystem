package dao;

import model.User;

import javax.servlet.ServletContextAttributeEvent;

public interface UserDao extends BaseDao{
    public boolean isUser(String userid, String password);
    public int getUserCount();
    public int getTravellerCount();
    public void writeCount(ServletContextAttributeEvent scae);
    public void save(User user);
    public void delete(User user);
    public User findUserById(String userid);
}

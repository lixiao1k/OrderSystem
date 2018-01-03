package dao;

import javax.servlet.ServletContextAttributeEvent;

public interface UserDao {
    public boolean isUser(String userid, String password);
    public void setUserCount(int count);
    public int getUserCount();
    public void setTravellerCount(int count);
    public int getTravellerCount();
    public void writeCount(ServletContextAttributeEvent scae);
}

package action.bussiness;

import java.io.Serializable;

public class UserCountBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userCount;
    private int travellerCount;

    public void setUserCount(int num){
        this.userCount = num;
    }

    public void setTravellerCount(int num){
        this.travellerCount = num;
    }

    public int getUserCount(){
        return userCount;
    }

    public int getTravellerCount(){
        return travellerCount;
    }
}

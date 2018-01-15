package dao;

import java.util.List;

public interface MyOrderDao extends BaseDao {

    public List getMyOrders(String name);
}

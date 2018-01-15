package dao.impl;

import dao.MyOrderDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;
import java.util.List;

public class MyOrderDaoImpl extends BaseDaoImpl implements MyOrderDao {
    private static MyOrderDaoImpl myOrderDao = new MyOrderDaoImpl();

    private MyOrderDaoImpl(){}

    public static MyOrderDaoImpl getInstance(){
        return myOrderDao;
    }

    @Override
    public List getMyOrders(String name) {
        Session session = HibernateUtil.getSession();
        Transaction tx =session.beginTransaction();
        Query query = session.createQuery("FROM Order as o WHERE o.userid=:name");
        query.setParameter("name",Integer.parseInt(name));
        List orders = query.list();
        tx.commit();
        session.close();
        return orders;
    }
}

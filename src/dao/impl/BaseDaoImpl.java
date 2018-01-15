package dao.impl;


import dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;

public class BaseDaoImpl implements BaseDao {

    public BaseDaoImpl(){

    }

    @Override
    public void flush() {
        HibernateUtil.getSession().flush();
    }

    @Override
    public void clear() {
        HibernateUtil.getSession().clear();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object load(Class c, int id) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            Object o = session.get(c, id);
            tx.commit();
            return o;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List getAllList(Class c) {
        return null;
    }

    @Override
    public Long getTotalCount(Class c) {
        return null;
    }


    @Override
    public void save(Object bean) {
        System.out.println("ready to getSession");
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.merge(bean);
        tx.commit();
    }

    @Override
    public void update(Object bean) {


    }

    @Override
    public void delete(Object bean) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            session.delete(bean);
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    @SuppressWarnings({ "rawtypes" })
    public void delete(Class c, int id) {

    }

    @Override
    @SuppressWarnings({ "rawtypes" })
    public void delete(Class c, int[] ids) {

    }
}

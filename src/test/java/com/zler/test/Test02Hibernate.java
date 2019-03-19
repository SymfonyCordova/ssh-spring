package com.zler.test;

import com.zler.domian.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class Test02Hibernate {

    @Test
    public void test(){
        Customer c = new Customer();
        c.setCustName("ssh整合Customer");
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();
        factory.close();
    }
}

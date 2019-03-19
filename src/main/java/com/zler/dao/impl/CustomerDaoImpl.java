package com.zler.dao.impl;

import com.zler.dao.CustomerDao;
import com.zler.domian.Customer;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

    @Override
    public List<Customer> findAllCustomer() {
        return (List<Customer>) getHibernateTemplate().find("from Customer");
    }

    @Override
    public void saveCustomer(Customer customer) {
        getHibernateTemplate().save(customer);
    }
}

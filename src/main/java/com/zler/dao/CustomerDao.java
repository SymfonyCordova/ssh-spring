package com.zler.dao;

import com.zler.domian.Customer;

import java.util.List;

public interface CustomerDao {

    /**
     * 查询所有客户
     * @return
     */
    List<Customer> findAllCustomer();

    /**
     * 保存客户
     * @param customer
     */
    void saveCustomer(Customer customer);
}

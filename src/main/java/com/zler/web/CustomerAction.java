package com.zler.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zler.domian.Customer;
import com.zler.service.CustomerService;
import java.util.List;

public class CustomerAction extends ActionSupport implements ModelDriven {
    //模型
    private Customer customer = new Customer();
    @Override
    public Object getModel() {
        return customer;
    }

    //栈中的值
    private List<Customer> customers;
    //----getters and setters------
    public List<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    //等待注入
    private CustomerService customerService;
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String addUICustomer(){
        return "addUICustomer";
    }

    public String findAllCustomer() {
        customers = customerService.findAllCustomer();
        return "findAllCustomer";
    }
}

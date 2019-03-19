package com.zler.test;

import com.zler.service.CustomerService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test01Spring {

    @Test
    public void test(){
        //获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //根据bean的id获取对象
        CustomerService customerService = (CustomerService)ac.getBean("customerService");
        customerService.findAllCustomer();
    }
}

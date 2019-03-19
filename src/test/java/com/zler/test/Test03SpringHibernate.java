package com.zler.test;

import com.zler.domian.Customer;
import com.zler.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class Test03SpringHibernate {

    @Autowired
    private CustomerService cs;

    @Test
    public void testSave(){
        Customer c = new Customer();
        c.setCustName("spring hibernate customer");
        cs.saveCustomer(c);
    }

    @Test
    public void testFindAll(){
        List list = cs.findAllCustomer();
        for (Object o:list){
            System.out.println(o);
        }
        List list1 = cs.findAllCustomer();
        for (Object o:list1){
            System.out.println(o);
        }
    }
}

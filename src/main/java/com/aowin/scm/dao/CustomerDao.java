package com.aowin.scm.dao;

import com.aowin.scm.pojo.Customer;
import com.aowin.scm.pojo.Vender;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

    List<Customer> findAllCustomer();

    List<Customer> findAll(Map<String, Object> params);

    int countAll();

    void addCustomer(Customer customer);

    void removeCustomer(String customerCode);

    Customer findCustomerByCode(String customerCode);

    void updateCustomer(Customer customer);

    List<Customer> searchCustomer(Map<String, Object> params);

    int countAllResults(Map<String, Object> params);

}

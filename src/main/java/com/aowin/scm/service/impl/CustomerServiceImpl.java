package com.aowin.scm.service.impl;

import com.aowin.scm.dao.CustomerDao;
import com.aowin.scm.pojo.Customer;
import com.aowin.scm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> findAllCustomer() {
        return customerDao.findAllCustomer();
    }

    @Override
    public List<Customer> findAll(Map<String, Object> params) {
        return customerDao.findAll(params);
    }

    @Override
    public int countAll() {
        return customerDao.countAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }

    @Override
    public void removeCustomer(String customerCode) {
        customerDao.removeCustomer(customerCode);
    }

    @Override
    public Customer findCustomerByCode(String customerCode) {
        return customerDao.findCustomerByCode(customerCode);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    @Override
    public List<Customer> searchCustomer(Map<String, Object> params) {
        return customerDao.searchCustomer(params);
    }

    @Override
    public int countAllResults(Map<String, Object> params) {
        return customerDao.countAllResults(params);
    }
}

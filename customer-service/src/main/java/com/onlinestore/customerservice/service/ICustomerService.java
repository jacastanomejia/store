package com.onlinestore.customerservice.service;

import com.onlinestore.customerservice.entity.Customer;
import com.onlinestore.customerservice.entity.Region;

import java.util.List;

public interface ICustomerService {

    public List<Customer> findAll();
    public Customer findById(Long id);

    public List<Customer> findByRegion(Region region);

    public Customer create(Customer customer);

    public Customer update(Customer customer);

    public boolean delete(Long id);


}

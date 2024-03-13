package com.onlinestore.customerservice.repository;


import com.onlinestore.customerservice.entity.Customer;
import com.onlinestore.customerservice.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByNumberId(String numberID);
    public List<Customer> findByName(String lastName);
    public List<Customer> findByRegion(Region region);

}

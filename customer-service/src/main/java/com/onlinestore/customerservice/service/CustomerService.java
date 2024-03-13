package com.onlinestore.customerservice.service;


import com.onlinestore.customerservice.entity.Customer;
import com.onlinestore.customerservice.entity.Region;
import com.onlinestore.customerservice.repository.ICustomerRepository;
import com.onlinestore.customerservice.repository.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IRegionRepository regionRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> oc = customerRepository.findById(id);
        if(oc.isEmpty()){
            return null;
        }

        return oc.get();
    }

    @Override
    public List<Customer> findByRegion(Region region) {
        if(region == null){
            return List.of();
        }
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer create(Customer customer) {
        if(customer.getRegion() == null){
            throw new RuntimeException("No region defined");
        }

        Optional<Region> or = regionRepository.findById(customer.getRegion().getId());
        if(or.isEmpty()){
            regionRepository.save(customer.getRegion());
        }

        customer.setCreatedAt(Calendar.getInstance().getTime());
        customer.setState("created");
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        if(customer.getRegion() == null){
            throw new RuntimeException("No region defined");
        }

        Optional<Region> or = regionRepository.findById(customer.getRegion().getId());
        if(or.isEmpty()){
            regionRepository.save(customer.getRegion());
        }

        Optional<Customer> oo = customerRepository.findById(customer.getId());
        if(oo.isEmpty()){
            return null;
        }
        Customer original = oo.get();
        original.setEmail(customer.getEmail());
        original.setName(customer.getName());
        original.setRegion(customer.getRegion());
        original.setState(original.getState());
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public boolean delete(Long id) {
        if(id == null){
            return false;
        }

        Optional<Customer> oc = customerRepository.findById(id);
        if(oc.isEmpty()){
            return false;
        }

        customerRepository.delete(oc.get());
        return true;
    }

}

package com.onlinestore.customerservice.controller;

import com.onlinestore.customerservice.controller.dto.CustomerDto;
import com.onlinestore.customerservice.controller.dto.ErrorUtils;
import com.onlinestore.customerservice.entity.Customer;
import com.onlinestore.customerservice.service.ICustomerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll(){
        List<Customer> customers = customerService.findAll();

        if(customers == null || customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CustomerDto> customerDtoList =  customers.stream()
                .map(custom -> modelMapper.map(custom, CustomerDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id){
        Customer customer = customerService.findById(id);

        if(customer == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelMapper.map(customer, CustomerDto.class));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody Customer customer, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorUtils.formatMessage(result));
        }

        customer = customerService.create(customer);
        return ResponseEntity.ok(modelMapper.map(customer, CustomerDto.class));
    }

    @PutMapping
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody Customer customer, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorUtils.formatMessage(result));
        }

        customer = customerService.update(customer);
        return ResponseEntity.ok(modelMapper.map(customer, CustomerDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        if(id == null){
            return ResponseEntity.notFound().build();
        }

        boolean success = customerService.delete(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(true);
    }


}

package com.card_management.controller;

import com.card_management.dto.customer.CustomerDto;
import com.card_management.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAllCustomer(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAllCustomer(pageable));
    }


    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return ResponseEntity.ok("Customer created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(customerDto);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

}

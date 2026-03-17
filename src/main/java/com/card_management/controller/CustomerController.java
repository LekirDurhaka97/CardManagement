package com.card_management.controller;

import com.card_management.dto.customer.CustomerDto;
import com.card_management.service.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<CustomerDto>> getAllCustomer(HttpServletRequest request, Pageable pageable) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        List<CustomerDto> customers = customerService.getAllCustomer(pageable);
        log.info("Response: {}", customers);
        return ResponseEntity.ok(customers);
    }


    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(HttpServletRequest request, @RequestBody CustomerDto customerDto) {
        log.info("Request: {} {} Body: {}", request.getMethod(), request.getRequestURI(), customerDto);
        customerService.createCustomer(customerDto);
        ResponseEntity<String> response = ResponseEntity.ok("Customer created successfully");
        log.info("Response: {}", response.getBody());
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(HttpServletRequest request, @RequestBody CustomerDto customerDto) {
        log.info("Request: {} {} Body: {}", request.getMethod(), request.getRequestURI(), customerDto);
        customerService.updateCustomer(customerDto);
        ResponseEntity<String> response = ResponseEntity.ok("Customer updated successfully");
        log.info("Response: {}", response.getBody());
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(HttpServletRequest request, @PathVariable UUID id) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        customerService.deleteCustomer(id);
        ResponseEntity<String> response = ResponseEntity.ok("Customer deleted successfully");
        log.info("Response: {}", response.getBody());
        return response;
    }

}

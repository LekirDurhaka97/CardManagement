package com.card_management.service.customer;

import com.card_management.dto.customer.CustomerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    boolean customerExistById(UUID id);
    List<CustomerDto> getAllCustomer(Pageable pageable);
    void createCustomer(CustomerDto customerDto);
    void updateCustomer(CustomerDto customerDto);
    void deleteCustomer(UUID id);
}

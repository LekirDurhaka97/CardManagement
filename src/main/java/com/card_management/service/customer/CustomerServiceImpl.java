package com.card_management.service.customer;

import com.card_management.dto.customer.CustomerDto;
import com.card_management.exception.GeneralException;
import com.card_management.mapper.CustomerMapper;
import com.card_management.model.customer.Customer;
import com.card_management.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    public boolean customerExistById(UUID id) {
        return customerRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomer(Pageable pageable) {
        Page<Customer> allCustomer = customerRepository.findAll(pageable);
        return allCustomer.stream().map(customer -> customerMapper.toDto(customer)).toList();
    }

    @Transactional
    public void createCustomer(CustomerDto dto) {
        Customer customer = customerMapper.toEntity(dto);
        customer.setId(null); // ensure new entity
        //TODO: can improve use the current session username - need to implement Spring Security and token access
        customer.setCreatedBy("system");
        customer.setCreatedTime(ZonedDateTime.now());
        customerRepository.save(customer);
    }

    @Transactional
    public void updateCustomer(CustomerDto dto) {
        Customer customer = customerRepository.findById(dto.id())
                .orElseThrow(() -> new GeneralException("Customer doesn't exist"));
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());
        //TODO: can improve use the current session username - need to implement Spring Security and token access
        customer.setModifiedBy("system");
        customer.setModifiedTime(ZonedDateTime.now());
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        if(customerRepository.existsById(id))
            customerRepository.deleteById(id);
        else throw new GeneralException("Customer doesn't exist");
    }

}

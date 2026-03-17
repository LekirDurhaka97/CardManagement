package com.card_management.mapper;

import com.card_management.dto.customer.CustomerDto;
import com.card_management.model.customer.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer card);
    Customer toEntity(CustomerDto dto);
}
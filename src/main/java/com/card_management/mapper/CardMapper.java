package com.card_management.mapper;

import com.card_management.dto.card.CardLimitDto;
import com.card_management.model.card.Card;
import com.card_management.dto.card.CardDto;
import com.card_management.model.card.CardLimit;
import com.card_management.model.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "customer.id", target = "customerId")
    CardDto cardToDto(Card card);

    @Mapping(source = "customerId", target = "customer")
    Card cardToEntity(CardDto dto);

    CardLimitDto cardLimitToDto(CardLimit cardLimit);

    CardLimit cardLimitToEntity(CardLimitDto dto);

    default Customer map(UUID id) {
        if (id == null) return null;
        return new Customer(id);
    }
}
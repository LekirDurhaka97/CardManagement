package com.card_management.service.card;

import com.card_management.dto.card.CardDto;
import com.card_management.dto.card.CardLimitDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CardService {
    List<CardDto> getAllCard(Pageable pageable);
    List<CardDto> getAllCardByCustomerId(UUID id, Pageable pageable);
    CardDto getCardByCardNumber(String cardNumber);
    CardLimitDto getCardLimitByCardNumber(String cardNumber);
    void createCard(CardDto cardDto);
    void updateCard(CardDto cardDto);
    boolean cardExistById(UUID id);
    void updateCardLimit(CardLimitDto dto);
    void deleteCard(UUID id);
}

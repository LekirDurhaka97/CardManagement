package com.card_management.controller;

import com.card_management.dto.card.CardDto;
import com.card_management.dto.card.CardLimitDto;
import com.card_management.dto.external.CountryDto;
import com.card_management.exception.GeneralException;
import com.card_management.service.card.CardService;
import com.card_management.service.customer.CustomerService;
import com.card_management.service.external.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CountryService countryService;

    //lookup: for the FE to use
    @GetMapping("/AllCountriesWithCurrencies")
    public ResponseEntity<List<CountryDto>> getAllCountriesWithCurrencies() {
        return ResponseEntity.ok(countryService.getAllCountriesWithCurrencies());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CardDto>> getAllCard(Pageable pageable) {
        return ResponseEntity.ok(cardService.getAllCard(pageable));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<CardDto>> getAllCardByCustomerId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(cardService.getAllCardByCustomerId(id, pageable));
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardDto> getCardByCardNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(cardService.getCardByCardNumber(cardNumber));
    }

    @GetMapping("/limit/{cardNumber}")
    public ResponseEntity<CardLimitDto> getCardLimitByCardNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(cardService.getCardLimitByCardNumber(cardNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCard(@RequestBody CardDto cardDto) {
        boolean customerExists = customerService.customerExistById(cardDto.customerId());
        if (!customerExists) throw new GeneralException("Customer doesn't exist yet");
        cardService.createCard(cardDto);
        return ResponseEntity.ok("Card created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCard(@RequestBody CardDto cardDto) {
        boolean customerExists = customerService.customerExistById(cardDto.customerId());
        if (!customerExists) throw new GeneralException("Customer doesn't exist yet");
        cardService.updateCard(cardDto);
        return ResponseEntity.ok("Card updated successfully");
    }

    @PutMapping("/update/limit")
    public ResponseEntity<String> updateCardLimit(@RequestBody CardLimitDto cardLimitDto) {
        boolean cardExists = cardService.cardExistById(cardLimitDto.cardId());
        if (!cardExists) throw new GeneralException("Card doesn't exist yet");
        cardService.updateCardLimit(cardLimitDto);
        return ResponseEntity.ok("Card updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable UUID id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Card deleted successfully");
    }

}
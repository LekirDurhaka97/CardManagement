package com.card_management.controller;

import com.card_management.dto.card.CardDto;
import com.card_management.dto.card.CardLimitDto;
import com.card_management.service.card.CardService;
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
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<CardDto>> getAllCard(HttpServletRequest request, Pageable pageable) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        List<CardDto> cards = cardService.getAllCard(pageable);
        log.info("Response: {}", cards);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<CardDto>> getAllCardByCustomerId(HttpServletRequest request, @PathVariable UUID id, Pageable pageable) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        List<CardDto> cards = cardService.getAllCardByCustomerId(id, pageable);
        log.info("Response: {}", cards);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardDto> getCardByCardNumber(HttpServletRequest request, @PathVariable String cardNumber) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        CardDto card = cardService.getCardByCardNumber(cardNumber);
        log.info("Response: {}", card);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/limit/{cardNumber}")
    public ResponseEntity<CardLimitDto> getCardLimitByCardNumber(HttpServletRequest request, @PathVariable String cardNumber) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        CardLimitDto limit = cardService.getCardLimitByCardNumber(cardNumber);
        log.info("Response: {}", limit);
        return ResponseEntity.ok(limit);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCard(HttpServletRequest request, @RequestBody CardDto cardDto) {
        log.info("Request: {} {} Body: {}", request.getMethod(), request.getRequestURI(), cardDto);
        boolean customerExists = customerService.customerExistById(cardDto.customerId());
        ResponseEntity<String> response;
        if (customerExists) {
            cardService.createCard(cardDto);
            response = ResponseEntity.ok("Card created successfully");
        } else response = ResponseEntity.badRequest().body("Customer doesn't exist yet");
        log.info("Response: {}", response.getBody());
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCard(HttpServletRequest request, @RequestBody CardDto cardDto) {
        log.info("Request: {} {} Body: {}", request.getMethod(), request.getRequestURI(), cardDto);
        boolean customerExists = customerService.customerExistById(cardDto.customerId());
        ResponseEntity<String> response;
        if (customerExists) {
            cardService.updateCard(cardDto);
            response = ResponseEntity.ok("Card updated successfully");
        } else response = ResponseEntity.badRequest().body("Customer doesn't exist yet");
        log.info("Response: {}", response.getBody());
        return response;
    }

    @PutMapping("/update/limit")
    public ResponseEntity<String> updateCardLimit(HttpServletRequest request, @RequestBody CardLimitDto cardLimitDto) {
        log.info("Request: {} {} Body: {}", request.getMethod(), request.getRequestURI(), cardLimitDto);
        boolean cardExists = cardService.cardExistById(cardLimitDto.cardId());
        ResponseEntity<String> response;
        if (cardExists) {
            cardService.updateCardLimit(cardLimitDto);
            response = ResponseEntity.ok("Card updated successfully");
        } else response = ResponseEntity.badRequest().body("Card doesn't exist yet");
        log.info("Response: {}", response.getBody());
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCard(HttpServletRequest request, @PathVariable UUID id) {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        cardService.deleteCard(id);
        ResponseEntity<String> response = ResponseEntity.ok("Card deleted successfully");
        log.info("Response: {}", response.getBody());
        return response;
    }

}
package com.card_management.service.card;

import com.card_management.dto.card.CardDto;
import com.card_management.dto.card.CardLimitDto;
import com.card_management.exception.GeneralException;
import com.card_management.mapper.CardMapper;
import com.card_management.model.card.Card;
import com.card_management.model.card.CardLimit;
import com.card_management.repository.CardLimitRepository;
import com.card_management.repository.CardRepository;
import com.card_management.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardMapper cardMapper;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardLimitRepository cardLimitRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CardDto> getAllCard(Pageable pageable) {
        Page<Card> allCard = cardRepository.findAll(pageable);
        return allCard.stream().map(card -> cardMapper.cardToDto(card)).toList();
    }

    @Transactional(readOnly = true)
    public List<CardDto> getAllCardByCustomerId(UUID id, Pageable pageable) {
        List<Card> allCardByCustomerId = customerRepository.findById(id)
                .orElseThrow(() -> new GeneralException("Customer doesn't exist")).getCards();
        return allCardByCustomerId.stream().map(card -> cardMapper.cardToDto(card)).toList();
    }

    @Transactional(readOnly = true)
    public CardDto getCardByCardNumber(String cardNumber) {
        Card cardByCardNumber = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new GeneralException("Card doesn't exist"));
        return cardMapper.cardToDto(cardByCardNumber);
    }

    @Transactional(readOnly = true)
    public CardLimitDto getCardLimitByCardNumber(String cardNumber) {
        UUID cardId = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new GeneralException("Card doesn't exist")).getId();
        CardLimit cardLimit = cardLimitRepository.findById(cardId)
                .orElseThrow(() -> new GeneralException("Card Limit doesn't exist"));
        return cardMapper.cardLimitToDto(cardLimit);
    }

    @Transactional
    public void createCard(CardDto cardDto) {
        Card card = cardMapper.cardToEntity(cardDto);
        card.setId(null); // ensure new entity
        //TODO: can improve use the current session username - need to implement Spring Security and token access
        card.setCreatedBy("system");
        card.setCreatedTime(ZonedDateTime.now());
        cardRepository.save(card);
        CardLimit cardLimit = new CardLimit(
                card.getId(),
                500,
                "MYR",
                0,
                ZonedDateTime.now()
        );
        cardLimitRepository.save(cardLimit);
    }

    @Transactional
    public void updateCard(CardDto dto) {
        Card card = cardRepository.findById(dto.id())
                .orElseThrow(() -> new GeneralException("Card doesn't exist"));
        card.setCardNumber(dto.cardNumber());
        card.setCardType(dto.cardType());
        card.setStatus(dto.status());
        //TODO: can improve use the current session username - need to implement Spring Security and token access
        card.setModifiedBy("system");
        card.setModifiedTime(ZonedDateTime.now());
    }

    public boolean cardExistById(UUID id) {
        return cardRepository.existsById(id);
    }

    @Transactional
    public void updateCardLimit(CardLimitDto dto) {
        Optional<CardLimit> cardLimitOpt = cardLimitRepository.findById(dto.cardId());
        if (cardLimitOpt.isPresent()) {
            CardLimit cardLimit = cardLimitOpt.get();
            cardLimit.setDailyLimit(dto.dailyLimit());
            cardLimit.setDailyLimitCurrency(dto.dailyLimitCurrency());
        }
    }

    @Transactional
    public void deleteCard(UUID id) {
        if(cardRepository.existsById(id))
            cardRepository.deleteById(id);
        else throw new GeneralException("Card doesn't exist");
    }

}

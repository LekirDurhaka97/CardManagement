package com.card_management.dto.card;

import java.time.ZonedDateTime;
import java.util.UUID;

public record CardDto(
        UUID id,
        String cardNumber,
        UUID customerId,
        //TODO: can be improved to use lookup/enum
        String status,
        //TODO: can be improved to use lookup/enum
        String cardType,
        ZonedDateTime expiryTime
) {}
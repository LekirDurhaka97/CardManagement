package com.card_management.dto.card;

import java.util.UUID;

public record CardLimitDto(
        UUID cardId,
        Integer dailyLimit,
        String dailyLimitCurrency,
        Integer usedToday
) {}

package com.card_management.dto.external;

import java.util.Map;

public record CountryDto(
        CountryNameDto name,
        Map<String, CurrencyDto> currencies
) {}

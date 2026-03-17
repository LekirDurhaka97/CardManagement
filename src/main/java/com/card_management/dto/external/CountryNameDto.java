package com.card_management.dto.external;

import java.util.Map;

public record CountryNameDto(
        String common,
        String official,
        Map<String, CountryNativeNameDto> nativeName
) {}

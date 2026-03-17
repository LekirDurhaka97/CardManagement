package com.card_management.service.external;

import com.card_management.dto.external.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountriesWithCurrencies();
}
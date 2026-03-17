package com.card_management.service.external;

import com.card_management.dto.external.CountryDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl {

    private final WebClient webClient;

    @Getter
    private List<CountryDto> countries;

    @PostConstruct
    public void init() {
        countries = webClient.get()
                .uri("https://restcountries.com/v3.1/all?fields=name,currencies")
                .retrieve()
                .bodyToFlux(CountryDto.class)
                .collectList()
                .block();
    }

}
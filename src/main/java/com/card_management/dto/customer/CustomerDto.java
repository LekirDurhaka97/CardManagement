package com.card_management.dto.customer;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String email,
        String phone
) {}

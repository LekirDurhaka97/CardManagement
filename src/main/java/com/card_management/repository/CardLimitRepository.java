package com.card_management.repository;

import com.card_management.model.card.CardLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardLimitRepository extends JpaRepository<CardLimit, UUID> {
}

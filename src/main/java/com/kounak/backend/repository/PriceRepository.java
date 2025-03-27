package com.kounak.backend.repository;

import com.kounak.backend.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query(value = "SELECT currentPrice FROM prices WHERE product_id = :productId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<BigDecimal> findLatestPriceByProductId(@Param("productId") Long productId);
}
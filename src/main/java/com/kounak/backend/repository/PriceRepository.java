package com.kounak.backend.repository;

import com.kounak.backend.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(value = "SELECT p.price FROM prices p WHERE p.product_id = :productId ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
    Double findLatestPriceByProductId(@Param("productId") Long productId);

    List<Price> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Price p WHERE p.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
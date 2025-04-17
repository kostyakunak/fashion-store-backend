package com.kounak.backend.repository;

import com.kounak.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
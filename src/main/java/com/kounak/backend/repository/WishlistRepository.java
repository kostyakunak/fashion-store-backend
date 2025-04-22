package com.kounak.backend.repository;

import com.kounak.backend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
    
    // Поиск записей по ID пользователя и ID продукта
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.product.id = :productId")
    List<Wishlist> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Wishlist w WHERE w.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
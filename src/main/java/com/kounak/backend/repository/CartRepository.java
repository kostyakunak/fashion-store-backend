package com.kounak.backend.repository;

import com.kounak.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    @EntityGraph(attributePaths = {"product", "user"})
    List<Cart> findByUserId(Long userId);
    
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId AND c.sizeId = :sizeId")
    List<Cart> findByUserIdAndProductIdAndSizeId(
        @Param("userId") Long userId, 
        @Param("productId") Long productId, 
        @Param("sizeId") Long sizeId
    );
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
    
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId")
    List<Cart> findByUserIdAndProductId(
        @Param("userId") Long userId,
        @Param("productId") Long productId
    );
    
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = c.quantity + :quantity WHERE c.id = :id")
    void incrementQuantity(@Param("id") Long id, @Param("quantity") int quantity);
    
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = c.quantity - :quantity WHERE c.id = :id AND c.quantity >= :quantity")
    int decrementQuantity(@Param("id") Long id, @Param("quantity") int quantity);
    
    @Query("SELECT COUNT(c) FROM Cart c WHERE c.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.user.id = :userId")
    Integer sumQuantityByUserId(@Param("userId") Long userId);
}
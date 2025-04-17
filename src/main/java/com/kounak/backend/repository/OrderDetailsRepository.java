package com.kounak.backend.repository;

import com.kounak.backend.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrderId(Long orderId);
    
    // Метод для проверки, используется ли товар в заказах
    List<OrderDetails> findByProductId(Long productId);
    
    // Метод для удаления деталей заказа по ID товара
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetails od WHERE od.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
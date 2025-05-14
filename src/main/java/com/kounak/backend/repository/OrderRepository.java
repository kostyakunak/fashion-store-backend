package com.kounak.backend.repository;

import com.kounak.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o IS NOT NULL")
    List<Order> findAllWithDetails();
    
    /**
     * Find orders by user ID
     */
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.user.id = :userId")
    List<Order> findByUserId(Long userId);
}
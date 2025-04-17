package com.kounak.backend.repository;

import com.kounak.backend.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Warehouse w WHERE w.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
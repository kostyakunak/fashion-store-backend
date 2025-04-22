package com.kounak.backend.repository;

import com.kounak.backend.model.Product;
import com.kounak.backend.model.Size;
import com.kounak.backend.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    // Находит записи по продукту
    List<Warehouse> findByProduct(Product product);
    
    // Находит записи по ID продукта и ID размера
    @Query("SELECT w FROM Warehouse w WHERE w.product.id = :productId AND w.size.id = :sizeId")
    List<Warehouse> findByProductIdAndSizeId(@Param("productId") Long productId, @Param("sizeId") Long sizeId);
    
    // Находит доступные размеры для продукта (где количество > 0)
    @Query("SELECT DISTINCT w.size FROM Warehouse w WHERE w.product.id = :productId AND w.quantity > 0")
    List<Size> findAvailableSizesForProduct(@Param("productId") Long productId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Warehouse w WHERE w.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
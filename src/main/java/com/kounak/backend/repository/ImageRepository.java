package com.kounak.backend.repository;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long productId); // Используем productId вместо объекта Product
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.product.id = :productId")
    void deleteByProductId(Long productId);
}
package com.kounak.backend.repository;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long productId); // Используем productId вместо объекта Product
}
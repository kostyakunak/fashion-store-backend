package com.kounak.backend.repository;

import com.kounak.backend.model.Category;
import com.kounak.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
    
    List<Product> findByArchivedFalse();
    
    List<Product> findByCategoryAndArchivedFalse(Category category);
    
    Page<Product> findByArchivedFalse(Pageable pageable);
}
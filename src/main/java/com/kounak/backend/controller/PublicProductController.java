package com.kounak.backend.controller;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.Price;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.ImageService;
import com.kounak.backend.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://kounak2.netlify.app"}) // Разрешаем CORS для фронтенд приложения
@RequestMapping("/products")
public class PublicProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final PriceService priceService;

    public PublicProductController(ProductService productService, ImageService imageService, PriceService priceService) {
        this.productService = productService;
        this.imageService = imageService;
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        
        // Преобразуем Product в формат, который ожидает фронтенд с дополнительной информацией
        List<Map<String, Object>> enrichedProducts = products.stream().map(product -> {
            // Получаем изображения для продукта
            List<Image> images = imageService.getImagesByProductId(product.getId());
            
            // Получаем цену продукта
            Price price = priceService.getLatestPriceByProductId(product.getId());
            
            // Создаем объект с нужной структурой
            return Map.of(
                "id", product.getId(),
                "name", product.getName(),
                "productDetails", product.getProductDetails() != null ? product.getProductDetails() : "",
                "measurements", product.getMeasurements() != null ? product.getMeasurements() : "",
                "category", product.getCategory(),
                "images", images,
                "price", price != null ? price.getOriginalPrice() : 0
            );
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(enrichedProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        List<Image> images = imageService.getImagesByProductId(id);
        Price price = priceService.getLatestPriceByProductId(id);
        
        Map<String, Object> enrichedProduct = Map.of(
            "id", product.getId(),
            "name", product.getName(),
            "productDetails", product.getProductDetails() != null ? product.getProductDetails() : "",
            "measurements", product.getMeasurements() != null ? product.getMeasurements() : "",
            "category", product.getCategory(),
            "images", images,
            "price", price != null ? price.getOriginalPrice() : 0
        );
        
        return ResponseEntity.ok(enrichedProduct);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Map<String, Object>>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        
        List<Map<String, Object>> enrichedProducts = products.stream().map(product -> {
            List<Image> images = imageService.getImagesByProductId(product.getId());
            Price price = priceService.getLatestPriceByProductId(product.getId());
            
            return Map.of(
                "id", product.getId(),
                "name", product.getName(),
                "productDetails", product.getProductDetails() != null ? product.getProductDetails() : "",
                "measurements", product.getMeasurements() != null ? product.getMeasurements() : "",
                "category", product.getCategory(),
                "images", images,
                "price", price != null ? price.getOriginalPrice() : 0
            );
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(enrichedProducts);
    }
} 
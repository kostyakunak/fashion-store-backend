package com.kounak.backend.controller;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.Price;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.ImageService;
import com.kounak.backend.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

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
        List<Product> products = productService.getActiveProducts();
        
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
        List<Product> products = productService.getActiveProductsByCategoryId(categoryId);

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

    @GetMapping("/chunked")
    public ResponseEntity<Map<String, Object>> getProductsChunked(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "up") String direction) {

        // Validate parameters
        if (page < 0) {
            throw new IllegalArgumentException("Page must be non-negative");
        }
        if (limit <= 0 || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }
        if (!"up".equals(direction) && !"down".equals(direction)) {
            throw new IllegalArgumentException("Direction must be 'up' or 'down'");
        }

        // Create sorting based on direction
        Sort sort = "down".equals(direction)
            ? Sort.by(Sort.Direction.DESC, "id")
            : Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Product> productPage = productService.getActiveProductsPaged(pageable);

        // Enrich products with images and prices
        List<Map<String, Object>> enrichedProducts = productPage.getContent().stream().map(product -> {
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

        // Build response with metadata
        Map<String, Object> response = new HashMap<>();
        response.put("products", enrichedProducts);
        response.put("currentPage", page);
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasMore", productPage.hasNext());

        return ResponseEntity.ok(response);
    }
}
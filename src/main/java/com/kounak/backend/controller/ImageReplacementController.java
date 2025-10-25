package com.kounak.backend.controller;

import com.kounak.backend.model.Product;
import com.kounak.backend.service.ImageReplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/image-replacement")
@PreAuthorize("hasRole('ADMIN')")
public class ImageReplacementController {

    private final ImageReplacementService imageReplacementService;

    @Autowired
    public ImageReplacementController(ImageReplacementService imageReplacementService) {
        this.imageReplacementService = imageReplacementService;
    }

    /**
     * Get list of products that need image replacement
     */
    @GetMapping("/products-needing-replacement")
    public ResponseEntity<List<Product>> getProductsNeedingReplacement() {
        List<Product> products = imageReplacementService.findProductsNeedingImageReplacement();
        return ResponseEntity.ok(products);
    }

    /**
     * Replace images for a specific product
     */
    @PostMapping("/replace/{productId}")
    public ResponseEntity<Map<String, Object>> replaceProductImages(@PathVariable Long productId) {
        boolean success = imageReplacementService.replaceProductImagesWithValidation(productId);

        Map<String, Object> response = Map.of(
            "success", success,
            "productId", productId,
            "message", success ? "Images replaced successfully with validation" : "Failed to replace images"
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Batch replace images for multiple products
     */
    @PostMapping("/batch-replace")
    public ResponseEntity<Map<String, Object>> batchReplaceImages(@RequestBody List<Long> productIds) {
        Map<Long, Boolean> results = imageReplacementService.batchReplaceImages(productIds);

        long successful = results.values().stream().mapToLong(success -> success ? 1 : 0).sum();
        long total = results.size();

        Map<String, Object> response = Map.of(
            "totalProcessed", total,
            "successful", successful,
            "failed", total - successful,
            "results", results
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Search Pexels for images (for testing purposes)
     */
    @GetMapping("/search-pexels")
    public ResponseEntity<List<String>> searchPexelsImages(
            @RequestParam String productName,
            @RequestParam(required = false) String categoryName) {

        List<String> imageUrls = imageReplacementService.searchPexelsImages(productName, categoryName);
        return ResponseEntity.ok(imageUrls);
    }
}
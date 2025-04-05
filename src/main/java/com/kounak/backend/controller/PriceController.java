package com.kounak.backend.controller;

import com.kounak.backend.model.Price;
import com.kounak.backend.model.Product;
import com.kounak.backend.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<Price> getAllPrices() {
        return priceService.getAllPrices();
    }

    @PostMapping
    public ResponseEntity<Price> addPrice(@RequestBody Price price) {
        if (price.getProduct() == null || price.getOriginalPrice() == null || price.getPrice() == null) {
            throw new RuntimeException("Product, original price and price are required");
        }
        if (price.getId() == null) {
            throw new RuntimeException("ID is required");
        }
        return ResponseEntity.ok(priceService.addPrice(price));
    }

    @PutMapping("/{id}")
    public Price updatePrice(@PathVariable Long id, @RequestBody Price price) {
        if (price.getProduct() == null) {
            throw new RuntimeException("Product is required");
        }
        return priceService.updatePrice(id, price);
    }

    @DeleteMapping("/{id}")
    public void deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
    }

    @GetMapping("/history/{productId}")
    public List<Price> getPriceHistory(@PathVariable Long productId) {
        return priceService.getPriceHistory(productId);
    }

    @GetMapping("/latest/{productId}")
    public BigDecimal getLatestPrice(@PathVariable Long productId) {
        return priceService.getLatestPrice(productId);
    }
}
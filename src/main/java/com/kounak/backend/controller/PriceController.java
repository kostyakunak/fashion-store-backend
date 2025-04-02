package com.kounak.backend.controller;

import com.kounak.backend.model.Price;
import com.kounak.backend.model.Product;
import com.kounak.backend.service.PriceService;
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

    @PostMapping
    public Price addPrice(@RequestBody Price price) {
        if (price.getProduct() == null || price.getOriginalPrice() == null || price.getPrice() == null) {
            throw new RuntimeException("Product, original price and price are required");
        }
        return priceService.addPrice(price.getProduct(), price.getOriginalPrice(), price.getPrice());
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
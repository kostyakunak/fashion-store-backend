package com.kounak.backend.service;

import com.kounak.backend.model.Price;
import com.kounak.backend.model.Product;
import com.kounak.backend.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price addPrice(Product product, BigDecimal originalPrice, BigDecimal price) {
        if (originalPrice == null || price == null) {
            throw new IllegalArgumentException("Original price and price cannot be null");
        }

        Price newPrice = new Price(product, originalPrice, price);
        return priceRepository.save(newPrice);
    }

    public BigDecimal getLatestPrice(Long productId) {
        Double price = priceRepository.findLatestPriceByProductId(productId);
        if (price == null) {
            throw new RuntimeException("Price not found for product ID: " + productId);
        }
        return BigDecimal.valueOf(price);
    }

    public List<Price> getPriceHistory(Long productId) {
        return priceRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
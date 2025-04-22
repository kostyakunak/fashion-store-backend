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

    public List<Price> getAllPrices() {
        return priceRepository.findAll().stream()
                .sorted((p1, p2) -> p1.getId().compareTo(p2.getId()))
                .toList();
    }

    public Price addPrice(Price price) {
        if (price.getOriginalPrice() == null || price.getPrice() == null) {
            throw new IllegalArgumentException("Original price and price cannot be null");
        }
        return priceRepository.save(price);
    }

    public Price updatePrice(Long id, Price price) {
        Price existingPrice = priceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Price not found with id: " + id));
        
        existingPrice.setOriginalPrice(price.getOriginalPrice());
        existingPrice.setPrice(price.getPrice());
        existingPrice.setProduct(price.getProduct());
        
        return priceRepository.save(existingPrice);
    }

    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }

    public BigDecimal getLatestPrice(Long productId) {
        Double price = priceRepository.findLatestPriceByProductId(productId);
        if (price == null) {
            throw new RuntimeException("Price not found for product ID: " + productId);
        }
        return BigDecimal.valueOf(price);
    }

    public Price getLatestPriceByProductId(Long productId) {
        List<Price> prices = priceRepository.findByProductIdOrderByCreatedAtDesc(productId);
        if (prices == null || prices.isEmpty()) {
            return null;
        }
        return prices.get(0);
    }

    public List<Price> getPriceHistory(Long productId) {
        return priceRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
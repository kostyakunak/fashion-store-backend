package com.kounak.backend.service;

import com.kounak.backend.model.Price;
import com.kounak.backend.model.Product;
import com.kounak.backend.repository.PriceRepository;
import com.kounak.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    public PriceService(PriceRepository priceRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }

    public Price addPrice(Price price) {
        if (price.getProduct() == null || price.getProduct().getId() == null) {
            throw new RuntimeException("Product is required for price entry");
        }
        if (price.getOriginalPrice() == null || price.getCurrentPrice() == null) {
            throw new RuntimeException("Original and Current prices are required");
        }

        Product product = productRepository.findById(price.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        price.setProduct(product);
        return priceRepository.save(price);
    }

    // 🔹 ДОБАВЛЕН метод для получения всех цен
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    // 🔹 ДОБАВЛЕН метод для удаления цены
    public void deletePrice(Long id) {
        if (!priceRepository.existsById(id)) {
            throw new RuntimeException("Price not found");
        }
        priceRepository.deleteById(id);
    }
}
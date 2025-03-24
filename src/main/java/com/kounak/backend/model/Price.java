package com.kounak.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Double originalPrice;  // Добавляем поле

    @Column(nullable = false)
    private Double currentPrice;  // Добавляем поле

    public Price() {}

    public Price(Product product, Double originalPrice, Double currentPrice) {
        this.product = product;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }

    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
}
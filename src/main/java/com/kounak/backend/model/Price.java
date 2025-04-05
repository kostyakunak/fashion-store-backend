package com.kounak.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "original_price", precision = 10, scale = 2)
    @JsonProperty("original_price")
    private BigDecimal originalPrice;

    @Column(name = "present_price", precision = 10, scale = 2)
    @JsonProperty("present_price")
    private BigDecimal price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Price() {
        this.createdAt = LocalDateTime.now();
    }

    public Price(Product product, BigDecimal originalPrice, BigDecimal price) {
        this.product = product;
        this.originalPrice = originalPrice;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
package com.kounak.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "size_id", nullable = false)
    private Long sizeId;

    @Column(nullable = false)
    private int quantity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Long getSizeId() { return sizeId; }
    public void setSizeId(Long sizeId) { this.sizeId = sizeId; }
    
    // Вспомогательный метод для установки productId
    public void setProductId(Long productId) {
        // Если продукт не установлен, создаем новый объект Product
        if (this.product == null) {
            this.product = new Product();
        }
        this.product.setId(productId);
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
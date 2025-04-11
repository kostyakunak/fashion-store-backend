package com.kounak.backend.dto;

import java.math.BigDecimal;

/**
 * DTO для работы с деталями заказа
 */
public class OrderDetailsDTO {
    
    private Long orderId;
    private Long productId;
    private Long sizeId;
    private int quantity;
    private BigDecimal price;
    
    // Конструкторы
    public OrderDetailsDTO() {}
    
    public OrderDetailsDTO(Long orderId, Long productId, Long sizeId, int quantity, BigDecimal price) {
        this.orderId = orderId;
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Геттеры и сеттеры
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Long getSizeId() {
        return sizeId;
    }
    
    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
} 
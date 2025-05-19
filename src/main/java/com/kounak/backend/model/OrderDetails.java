package com.kounak.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnoreProperties({"items"})
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    // Цена фиксируется при заказе
    @Column(name = "price_at_purchase", nullable = false)
    private BigDecimal priceAtPurchase;

    @Column(nullable = false)
    private int quantity;

    // Удаляем ненужное поле price
    // private double price;

    // enrichment для фронта: productMap
    @Transient
    private Map<String, Object> productMap;

    // Конструкторы
    public OrderDetails() {}

    public OrderDetails(Order order, Product product, Size size, int quantity, BigDecimal priceAtPurchase) {
        this.order = order;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Убираем старый геттер и сеттер для price
    // public double getPrice() { return price; }
    // public void setPrice(double price) { this.price = price; }

    public Map<String, Object> getProductMap() {
        return productMap;
    }
    public void setProductMap(Map<String, Object> productMap) {
        this.productMap = productMap;
    }
}
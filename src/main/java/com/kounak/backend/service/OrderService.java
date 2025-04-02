package com.kounak.backend.service;

import com.kounak.backend.model.*;
import com.kounak.backend.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final PriceRepository priceRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, UserRepository userRepository, PriceRepository priceRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.userRepository = userRepository;
        this.priceRepository = priceRepository;
    }

    // ✅ Создание нового заказа
    public Order createOrder(Order order, List<OrderDetails> items) {
        if (order.getUser() == null || order.getUser().getId() == null) {
            throw new RuntimeException("User is required for order creation");
        }

        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Order must contain at least one item");
        }

        Order savedOrder = orderRepository.save(order);
        System.out.println("Order created: " + savedOrder.getId());

        for (OrderDetails item : items) {
            if (item.getProduct() == null || item.getSize() == null) {
                throw new RuntimeException("Each order item must have a valid product and size");
            }

            Double price = priceRepository.findLatestPriceByProductId(item.getProduct().getId());
            if (price == null) {
                throw new RuntimeException("Price not found for product ID: " + item.getProduct().getId());
            }

            item.setOrder(savedOrder);
            item.setPriceAtPurchase(BigDecimal.valueOf(price));
            orderDetailsRepository.save(item);
            System.out.println("Order item saved: " + item.getId() + " | Price: " + price);
        }

        return savedOrder;
    }

    // ✅ Получение всех заказов
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Обновление статуса заказа (конвертируем String → OrderStatus)
    public Order updateOrder(Long id, String status) {
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long id) {
        return updateOrder(id, "CANCELLED");
    }


    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

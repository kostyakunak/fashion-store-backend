package com.kounak.backend.service;

import com.kounak.backend.model.*;
import com.kounak.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final PriceRepository priceRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
        order.setTotalPrice(BigDecimal.ZERO);

        // Удаляем id, если вдруг был установлен
        order.setId(null);

        // Инициализируем коллекцию, если она null
        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        // Сохраняем заказ (id сгенерируется базой)
        Order savedOrder = orderRepository.save(order);

        // Добавляем детали заказа через коллекцию items
        if (items != null && !items.isEmpty()) {
            for (OrderDetails item : items) {
                item.setOrder(savedOrder);
                savedOrder.getItems().add(item);
            }
            // Сохраняем заказ с новыми items (Hibernate сам сохранит OrderDetails)
            savedOrder = orderRepository.save(savedOrder);
        }

        // Пересчитываем сумму заказа (если не используем триггер)
        BigDecimal total = recalculateOrderTotal(savedOrder.getId());
        savedOrder.setTotalPrice(total);
        orderRepository.save(savedOrder);

        return savedOrder;
    }

    // Новый метод для пересчёта суммы заказа
    public BigDecimal recalculateOrderTotal(Long orderId) {
        List<OrderDetails> details = orderDetailsRepository.findByOrderId(orderId);
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetails item : details) {
            if (item != null && item.getPriceAtPurchase() != null) {
                total = total.add(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        return total;
    }

    // ✅ Получение всех заказов
    public List<Order> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAllWithDetails();
            if (orders == null) {
                logger.warn("Order repository returned null");
                return new ArrayList<>();
            }
            
            for (Order order : orders) {
                try {
                    if (order.getItems() != null) {
                        BigDecimal totalPrice = BigDecimal.ZERO;
                        for (OrderDetails item : order.getItems()) {
                            if (item != null && item.getPriceAtPurchase() != null) {
                                totalPrice = totalPrice.add(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
                            }
                        }
                        order.setTotalPrice(totalPrice);
                    }
                } catch (Exception e) {
                    logger.error("Error calculating total price for order {}: {}", order.getId(), e.getMessage());
                    order.setTotalPrice(BigDecimal.ZERO);
                }
            }
            return orders;
        } catch (Exception e) {
            logger.error("Error fetching orders: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
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

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    public Order updateOrder(Order order) {
        // Получаем существующий заказ
        Order existingOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Обновляем только измененные поля
        if (order.getStatus() != null) {
            existingOrder.setStatus(order.getStatus());
        }
        if (order.getUser() != null) {
            existingOrder.setUser(order.getUser());
        }
        
        // Сохраняем только обновленные поля
        return orderRepository.save(existingOrder);
    }
    
    /**
     * Get all orders for a specific user
     */
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    /**
     * Get order details for a specific order
     */
    public List<OrderDetails> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }
}

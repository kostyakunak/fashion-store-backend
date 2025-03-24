package com.kounak.backend.service;

import com.kounak.backend.model.Order;
import com.kounak.backend.model.OrderStatus;
import com.kounak.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Добавление нового заказа
    public Order addOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now()); // Фиксируем время заказа
        order.setStatus(OrderStatus.PENDING);   // По умолчанию заказ "Ожидает обработки"
        return orderRepository.save(order);
    }

    public Order createOrder(Order order) {
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.AWAITING_PAYMENT); // Статус по умолчанию
        }
        return orderRepository.save(order);
    }

    // Получение всех заказов
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Обновление заказа (например, изменение статуса)
    public Order updateOrder(Long id, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setStatus(order.getStatus()); // Меняем статус (SHIPPED, DELIVERED, CANCELLED)
            return orderRepository.save(updatedOrder);
        }
        throw new RuntimeException("Order not found");
    }

    // Отмена заказа (изменение статуса на CANCELLED)
    public Order cancelOrder(Long id) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }

    // Удаление заказа
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
package com.kounak.backend.controller;

import com.kounak.backend.model.*;
import com.kounak.backend.service.OrderService;
import com.kounak.backend.service.UserService;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.SizeService;
import com.kounak.backend.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final SizeService sizeService;
    private final AddressService addressService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService, UserService userService,
                          ProductService productService, SizeService sizeService, AddressService addressService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.sizeService = sizeService;
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> payload) {
        try {
            // Проверяем наличие userId
            Object userIdObj = payload.get("userId");
            if (userIdObj == null) {
                return ResponseEntity.badRequest().body("userId is required");
            }

            // Получаем пользователя
            Long userId = Long.valueOf(userIdObj.toString());
            User user = userService.getUserById(userId);

            // Создаем заказ
            Order order = new Order();
            order.setUser(user);
            
            // Устанавливаем статус
            String status = (String) payload.get("status");
            if (status != null) {
                order.setStatus(OrderStatus.valueOf(status));
            }

            // Устанавливаем адрес, если передан addressId
            if (payload.containsKey("addressId") && payload.get("addressId") != null) {
                Long addressId = Long.valueOf(payload.get("addressId").toString());
                addressService.getAddressById(addressId).ifPresent(order::setAddress);
            }

            // Создаем заказ
            Order savedOrder = orderService.createOrder(order, new ArrayList<>());
            return ResponseEntity.ok(savedOrder);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        logger.info("Getting all orders");
        try {
            List<Order> orders = orderService.getAllOrders();
            if (orders == null) {
                logger.warn("Order service returned null");
                return ResponseEntity.ok(new ArrayList<>());
            }
            logger.info("Found {} orders", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка при загрузке данных. Пожалуйста, попробуйте позже.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            // Получаем существующий заказ
            Order order = orderService.getOrderById(id);
            
            // Обновляем статус, если он указан
            if (payload.containsKey("status")) {
                try {
                    String status = (String) payload.get("status");
                    order.setStatus(OrderStatus.valueOf(status));
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("Invalid order status: " + payload.get("status"));
                }
            }
            
            // Обновляем пользователя, если он указан
            if (payload.containsKey("userId")) {
                Long userId = Long.valueOf(payload.get("userId").toString());
                User user = userService.getUserById(userId);
                order.setUser(user);
            }

            // Обновляем адрес, если указан
            if (payload.containsKey("addressId") && payload.get("addressId") != null) {
                Long addressId = Long.valueOf(payload.get("addressId").toString());
                addressService.getAddressById(addressId).ifPresent(order::setAddress);
            }
            
            // Сохраняем изменения
            Order updatedOrder = orderService.updateOrder(order);
            return ResponseEntity.ok(updatedOrder);
            
        } catch (Exception e) {
            logger.error("Error updating order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating order: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        logger.info("Deleting order with ID: {}", id);
        try {
            orderService.deleteOrder(id);
            logger.info("Successfully deleted order with ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting order: " + e.getMessage());
        }
    }
}
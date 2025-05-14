package com.kounak.backend.controller;

import com.kounak.backend.exception.AuthException;
import com.kounak.backend.model.Order;
import com.kounak.backend.model.OrderDetails;
import com.kounak.backend.model.User;
import com.kounak.backend.service.OrderService;
import com.kounak.backend.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderApiController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderApiController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Get the authenticated user from the security context
     */
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        return user.orElseThrow(() -> new AuthException("Authentication required"));
    }

    /**
     * Verify that the order belongs to the authenticated user
     */
    private void verifyOrderOwnership(Long orderId) {
        User authenticatedUser = getAuthenticatedUser();
        Order order = orderService.getOrderById(orderId);
        
        if (!order.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AuthException("Not authorized to access this order");
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<Order> orders = orderService.getOrdersByUserId(authenticatedUser.getId());
            return ResponseEntity.ok(orders);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching orders: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            verifyOrderOwnership(orderId);
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching order: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        try {
            verifyOrderOwnership(orderId);
            List<OrderDetails> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching order details: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> payload) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            
            // Create order logic here
            // This would typically involve:
            // 1. Creating an Order entity with the authenticated user
            // 2. Converting cart items to OrderDetails
            // 3. Saving the order and its details
            
            // This is a placeholder implementation
            Order order = new Order();
            order.setUser(authenticatedUser);
            // Set other order properties from payload
            
            Order savedOrder = orderService.createOrder(order, List.of());
            return ResponseEntity.ok(savedOrder);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating order: " + e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            verifyOrderOwnership(orderId);
            Order order = orderService.getOrderById(orderId);
            
            // Implement cancellation logic
            // This would typically involve updating the order status to CANCELLED
            
            Order updatedOrder = orderService.updateOrder(order);
            return ResponseEntity.ok(updatedOrder);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error cancelling order: " + e.getMessage()));
        }
    }
}
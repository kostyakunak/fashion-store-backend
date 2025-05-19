package com.kounak.backend.controller;

import com.kounak.backend.exception.AuthException;
import com.kounak.backend.model.Order;
import com.kounak.backend.model.OrderDetails;
import com.kounak.backend.model.User;
import com.kounak.backend.service.OrderService;
import com.kounak.backend.service.UserService;
import com.kounak.backend.service.ImageService;
import com.kounak.backend.model.Image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderApiController {

    private final OrderService orderService;
    private final UserService userService;
    private final ImageService imageService;

    public OrderApiController(OrderService orderService, UserService userService, ImageService imageService) {
        this.orderService = orderService;
        this.userService = userService;
        this.imageService = imageService;
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
        System.out.println(">>> getMyOrders called");
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<Order> orders = orderService.getOrdersByUserId(authenticatedUser.getId());
            // enrich products with mainImageUrl
            for (Order order : orders) {
                if (order.getItems() != null) {
                    for (OrderDetails item : order.getItems()) {
                        if (item.getProduct() != null) {
                            Long productId = item.getProduct().getId();
                            List<Image> images = imageService.getImagesByProductId(productId);
                            String mainImageUrl = null;
                            if (images != null && !images.isEmpty()) {
                                // Сначала ищем isMain, потом sortOrder=0, иначе первое
                                mainImageUrl = images.stream().filter(img -> Boolean.TRUE.equals(img.getIsMain())).map(Image::getImageUrl).findFirst()
                                        .orElse(images.stream().filter(img -> img.getSortOrder() != null && img.getSortOrder() == 0).map(Image::getImageUrl).findFirst()
                                        .orElse(images.get(0).getImageUrl()));
                            }
                            // enrich product with mainImageUrl (через map, чтобы не ломать сериализацию)
                            if (mainImageUrl != null) {
                                // Костыль: enrich product через reflection-like map
                                Map<String, Object> productMap = new HashMap<>();
                                productMap.put("id", item.getProduct().getId());
                                productMap.put("name", item.getProduct().getName());
                                productMap.put("mainImageUrl", mainImageUrl);
                                // Можно добавить другие поля по необходимости
                                item.setProductMap(productMap); // потребуется добавить setProductMap в OrderDetails
                            }
                        }
                    }
                }
            }
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
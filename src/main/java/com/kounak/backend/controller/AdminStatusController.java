package com.kounak.backend.controller;

import com.kounak.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for providing admin status information
 * This is used by the admin dashboard to check system status and counts
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatusController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private WishlistRepository wishlistRepository;

    /**
     * General system status information
     * @return System status and timestamp
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "online");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Count of products in the system
     * @return Total number of products
     */
    @GetMapping("/products/count")
    public ResponseEntity<Map<String, Object>> getProductCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", productRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Count of categories in the system
     * @return Total number of categories
     */
    @GetMapping("/categories/count")
    public ResponseEntity<Map<String, Object>> getCategoryCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", categoryRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Count of orders in the system
     * @return Total number of orders
     */
    @GetMapping("/orders/count")
    public ResponseEntity<Map<String, Object>> getOrderCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", orderRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Count of users in the system
     * @return Total number of users
     */
    @GetMapping("/users/count")
    public ResponseEntity<Map<String, Object>> getUserCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", userRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Count of addresses in the system
     * @return Total number of addresses
     */
    @GetMapping("/addresses/count")
    public ResponseEntity<Map<String, Object>> getAddressCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", addressRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Count of images in the system
     * @return Total number of images
     */
    @GetMapping("/images/count")
    public ResponseEntity<Map<String, Object>> getImageCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", imageRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get counts for all entities
     * @return Counts for all major entities in the system
     */
    @GetMapping("/counts")
    public ResponseEntity<Map<String, Object>> getAllCounts() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("products", productRepository.count());
        response.put("categories", categoryRepository.count());
        response.put("orders", orderRepository.count());
        response.put("users", userRepository.count());
        response.put("addresses", addressRepository.count());
        response.put("images", imageRepository.count());
        response.put("carts", cartRepository.count());
        response.put("wishlists", wishlistRepository.count());
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
}
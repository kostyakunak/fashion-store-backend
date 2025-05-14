package com.kounak.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * A controller that provides public cart functionality by delegating to CartApiController.
 * This class exists to maintain the correct class name that matches its filename.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/public-cart")
public class PublicCartController {

    private final CartApiController cartApiController;

    @Autowired
    public PublicCartController(CartApiController cartApiController) {
        this.cartApiController = cartApiController;
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyCart() {
        return cartApiController.getMyCart();
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        return cartApiController.addToCart(request);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
        return cartApiController.removeFromCart(cartItemId);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Integer> request) {
        return cartApiController.updateCartItemQuantity(cartItemId, request);
    }
    
    @PutMapping("/{cartItemId}/size")
    public ResponseEntity<?> updateCartItemSize(
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Long> request) {
        return cartApiController.updateCartItemSize(cartItemId, request);
    }
    
    @PostMapping("/merge")
    public ResponseEntity<?> mergeGuestCart(@RequestBody Map<String, Object> request) {
        return cartApiController.mergeGuestCart(request);
    }
}
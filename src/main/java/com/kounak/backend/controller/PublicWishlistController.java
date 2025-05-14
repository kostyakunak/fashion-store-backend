package com.kounak.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * A controller that provides public wishlist functionality by delegating to WishlistApiController.
 * This class exists to maintain the correct class name that matches its filename.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/public-wishlist")
public class PublicWishlistController {

    private final WishlistApiController wishlistApiController;

    @Autowired
    public PublicWishlistController(WishlistApiController wishlistApiController) {
        this.wishlistApiController = wishlistApiController;
    }

    @GetMapping("/my")
    public ResponseEntity<List<Map<String, Object>>> getMyWishlist() {
        return wishlistApiController.getMyWishlist();
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody Map<String, Object> request) {
        return wishlistApiController.addToWishlist(request);
    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long wishlistItemId) {
        return wishlistApiController.removeFromWishlist(wishlistItemId);
    }
    
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable Long productId) {
        return wishlistApiController.removeProductFromWishlist(productId);
    }
}
package com.kounak.backend.controller;

import com.kounak.backend.model.Wishlist;
import com.kounak.backend.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public Wishlist addToWishlist(@RequestBody Wishlist wishlist) {
        return wishlistService.addToWishlist(wishlist);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getUserWishlist(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }

    @DeleteMapping("/{id}")
    public void removeFromWishlist(@PathVariable Long id) {
        wishlistService.removeFromWishlist(id);
    }
}
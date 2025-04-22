package com.kounak.backend.service;

import com.kounak.backend.model.Wishlist;
import com.kounak.backend.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist addToWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getUserWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public List<Wishlist> getUserWishlistByProductId(Long userId, Long productId) {
        return wishlistRepository.findByUserIdAndProductId(userId, productId);
    }

    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}
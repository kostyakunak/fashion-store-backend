package com.kounak.backend.service;

import com.kounak.backend.model.Cart;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.User;
import com.kounak.backend.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> getUserCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public List<Cart> getCartByUserIdAndProductIdAndSizeId(Long userId, Long productId, Long sizeId) {
        return cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId);
    }

    public Cart getCartItemById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Элемент корзины не найден с ID: " + id));
    }

    public Cart updateCart(Long id, Cart cart) {
        Optional<Cart> existingCart = cartRepository.findById(id);
        if (existingCart.isPresent()) {
            cart.setId(id);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("Cart item not found");
    }

    public Cart updateCartItem(Long id, Cart cart) {
        Optional<Cart> existingCart = cartRepository.findById(id);
        if (existingCart.isPresent()) {
            cart.setId(id);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("Cart item not found");
    }

    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }
    
    /**
     * Merges a guest cart with the authenticated user's cart
     * @param user The authenticated user
     * @param guestCartItems List of cart items from the guest cart
     * @return List of the user's cart items after merging
     */
    @Transactional
    public List<Cart> mergeGuestCart(User user, List<Map<String, Object>> guestCartItems) {
        if (guestCartItems == null || guestCartItems.isEmpty()) {
            return getUserCart(user.getId());
        }
        
        List<Cart> mergedItems = new ArrayList<>();
        
        for (Map<String, Object> guestItem : guestCartItems) {
            try {
                Long productId = Long.valueOf(guestItem.get("productId").toString());
                Long sizeId = Long.valueOf(guestItem.get("sizeId").toString());
                int quantity = Integer.parseInt(guestItem.get("quantity").toString());
                
                // Check if this product+size combination already exists in user's cart
                List<Cart> existingItems = getCartByUserIdAndProductIdAndSizeId(
                    user.getId(), productId, sizeId);
                
                if (!existingItems.isEmpty()) {
                    // Update existing cart item
                    Cart existingItem = existingItems.get(0);
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    mergedItems.add(updateCartItem(existingItem.getId(), existingItem));
                } else {
                    // Create a new cart item
                    Cart newItem = new Cart();
                    newItem.setUser(user);
                    
                    // Create a Product with just the ID
                    Product product = new Product();
                    product.setId(productId);
                    
                    newItem.setProduct(product);
                    newItem.setSizeId(sizeId);
                    newItem.setQuantity(quantity);
                    
                    mergedItems.add(addToCart(newItem));
                }
            } catch (Exception e) {
                // Log error and continue with next item
                System.err.println("Error merging cart item: " + e.getMessage());
            }
        }
        
        // Return the complete updated cart
        return getUserCart(user.getId());
    }
    
    /**
     * Clear all items from a user's cart
     * @param userId The user ID
     */
    @Transactional
    public void clearUserCart(Long userId) {
        List<Cart> userCartItems = getUserCart(userId);
        for (Cart item : userCartItems) {
            removeFromCart(item.getId());
        }
    }
}
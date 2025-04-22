package com.kounak.backend.service;

import com.kounak.backend.model.Cart;
import com.kounak.backend.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
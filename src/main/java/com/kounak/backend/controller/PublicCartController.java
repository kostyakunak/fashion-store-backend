package com.kounak.backend.controller;

import com.kounak.backend.model.Cart;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.User;
import com.kounak.backend.service.CartService;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cart")
public class PublicCartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public PublicCartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getUserCart(@PathVariable Long userId) {
        List<Cart> cartItems = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            Long productId = Long.parseLong(request.get("productId").toString());
            Long sizeId = Long.parseLong(request.get("sizeId").toString());
            int quantity = Integer.parseInt(request.get("quantity").toString());
            
            // Проверяем существование пользователя
            User user = userService.getUserById(userId);
            
            // Получаем объект продукта
            Product product = productService.getProductById(productId);
            
            // Проверяем, есть ли уже такой товар в корзине
            List<Cart> existingItems = cartService.getCartByUserIdAndProductIdAndSizeId(
                userId, productId, sizeId);
            
            if (!existingItems.isEmpty()) {
                // Если товар уже есть, обновляем количество
                Cart existingItem = existingItems.get(0);
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartService.updateCartItem(existingItem.getId(), existingItem);
                return ResponseEntity.ok(existingItem);
            } else {
                // Если товара нет, создаем новый элемент корзины
                Cart cartItem = new Cart();
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setSizeId(sizeId);
                cartItem.setQuantity(quantity);
                
                Cart savedItem = cartService.addToCart(cartItem);
                return ResponseEntity.ok(savedItem);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
        try {
            cartService.removeFromCart(cartItemId);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long cartItemId, 
            @RequestBody Map<String, Integer> request) {
        try {
            int quantity = request.get("quantity");
            if (quantity <= 0) {
                // Если количество <= 0, удаляем товар из корзины
                cartService.removeFromCart(cartItemId);
                return ResponseEntity.ok(Map.of("success", true, "removed", true));
            } else {
                // Иначе обновляем количество
                Cart cart = cartService.getCartItemById(cartItemId);
                cart.setQuantity(quantity);
                Cart updatedCart = cartService.updateCartItem(cartItemId, cart);
                return ResponseEntity.ok(updatedCart);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{cartItemId}/size")
    public ResponseEntity<?> updateCartItemSize(
            @PathVariable Long cartItemId, 
            @RequestBody Map<String, Long> request) {
        try {
            Long sizeId = request.get("sizeId");
            Cart cart = cartService.getCartItemById(cartItemId);
            cart.setSizeId(sizeId);
            Cart updatedCart = cartService.updateCartItem(cartItemId, cart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 
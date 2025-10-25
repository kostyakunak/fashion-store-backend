package com.kounak.backend.controller;

import com.kounak.backend.exception.AuthException;
import com.kounak.backend.exception.CartException;
import com.kounak.backend.model.Cart;
import com.kounak.backend.model.Image;
import com.kounak.backend.model.Price;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.User;
import com.kounak.backend.service.CartService;
import com.kounak.backend.service.ImageService;
import com.kounak.backend.service.PriceService;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final PriceService priceService;
    private final ImageService imageService;

    public CartApiController(CartService cartService, UserService userService, ProductService productService,
                            PriceService priceService, ImageService imageService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.priceService = priceService;
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
     * Verify that the cart item belongs to the authenticated user
     */
    private void verifyCartOwnership(Long cartItemId) {
        User authenticatedUser = getAuthenticatedUser();
        Cart cart = cartService.getCartItemById(cartItemId);
        
        if (!cart.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AuthException("Not authorized to access this cart item");
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<Map<String, Object>>> getMyCart() {
        User authenticatedUser = getAuthenticatedUser();
        List<Cart> cartItems = cartService.getCartByUserId(authenticatedUser.getId());
        
        // Enrich cart items with product details, prices, and images
        List<Map<String, Object>> enrichedCartItems = cartItems.stream()
            .map(cartItem -> {
                Product product = cartItem.getProduct();
                
                // Get price for the product
                Price price = priceService.getLatestPriceByProductId(product.getId());
                
                // Get main image for the product
                List<Image> images = imageService.getImagesByProductId(product.getId());
                String imageUrl = "https://via.placeholder.com/100"; // Default placeholder
                
                // Find the main image or use the first one
                if (images != null && !images.isEmpty()) {
                    // Try to find the main image first
                    Image mainImage = images.stream()
                        .filter(img -> img.getIsMain() != null && img.getIsMain())
                        .findFirst()
                        .orElse(images.get(0)); // Otherwise use the first image
                    
                    imageUrl = mainImage.getImageUrl();
                }
                
                // Create enriched cart item with all required fields
                Map<String, Object> enrichedItem = new HashMap<>();
                enrichedItem.put("id", cartItem.getId());
                enrichedItem.put("productId", product.getId());
                enrichedItem.put("name", product.getName());
                enrichedItem.put("price", price != null ? price.getOriginalPrice() : 0);
                enrichedItem.put("quantity", cartItem.getQuantity());
                enrichedItem.put("sizeId", cartItem.getSizeId());
                enrichedItem.put("imageUrl", imageUrl);
                
                return enrichedItem;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(enrichedCartItems);
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            Long productId = Long.parseLong(request.get("productId").toString());
            Long sizeId = Long.parseLong(request.get("sizeId").toString());
            int quantity = Integer.parseInt(request.get("quantity").toString());
            
            // Get product
            Product product = productService.getProductById(productId);
            
            // Check if the product already exists in the cart
            List<Cart> existingItems = cartService.getCartByUserIdAndProductIdAndSizeId(
                authenticatedUser.getId(), productId, sizeId);
            
            if (!existingItems.isEmpty()) {
                // If the product already exists, update the quantity
                Cart existingItem = existingItems.get(0);
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartService.updateCartItem(existingItem.getId(), existingItem);
                System.out.println("Updated existing cart item for user " + authenticatedUser.getId() + 
                                 ", product " + productId + ", size " + sizeId + 
                                 ", new quantity: " + existingItem.getQuantity());
                return ResponseEntity.ok(existingItem);
            } else {
                // If the product doesn't exist, create a new cart item
                Cart cartItem = new Cart();
                cartItem.setUser(authenticatedUser);
                cartItem.setProduct(product);
                cartItem.setSizeId(sizeId);
                cartItem.setQuantity(quantity);
                
                Cart savedItem = cartService.addToCart(cartItem);
                System.out.println("Created new cart item for user " + authenticatedUser.getId() + 
                                 ", product " + productId + ", size " + sizeId + 
                                 ", quantity: " + quantity);
                return ResponseEntity.ok(savedItem);
            }
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
        try {
            verifyCartOwnership(cartItemId);
            cartService.removeFromCart(cartItemId);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Integer> request) {
        try {
            verifyCartOwnership(cartItemId);
            int quantity = request.get("quantity");
            
            if (quantity <= 0) {
                // If quantity <= 0, remove the item from the cart
                cartService.removeFromCart(cartItemId);
                return ResponseEntity.ok(Map.of("success", true, "removed", true));
            } else {
                // Otherwise update the quantity
                Cart cart = cartService.getCartItemById(cartItemId);
                cart.setQuantity(quantity);
                Cart updatedCart = cartService.updateCartItem(cartItemId, cart);
                return ResponseEntity.ok(updatedCart);
            }
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{cartItemId}/size")
    public ResponseEntity<?> updateCartItemSize(
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Long> request) {
        try {
            verifyCartOwnership(cartItemId);
            Long sizeId = request.get("sizeId");
            Cart cart = cartService.getCartItemById(cartItemId);
            cart.setSizeId(sizeId);
            Cart updatedCart = cartService.updateCartItem(cartItemId, cart);
            return ResponseEntity.ok(updatedCart);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Merge a guest cart with the authenticated user's cart
     * @param guestCart The guest cart data from localStorage
     * @return The merged cart data
     */
    @PostMapping("/merge")
    public ResponseEntity<?> mergeGuestCart(@RequestBody Map<String, Object> request) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> guestCartItems = (List<Map<String, Object>>) request.get("guestCart");
            
            // Merge the guest cart with the user's cart
            List<Cart> mergedCart = cartService.mergeGuestCart(authenticatedUser, guestCartItems);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cart merged successfully");
            response.put("cart", mergedCart);
            
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (CartException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to merge cart: " + e.getMessage()));
        }
    }
}
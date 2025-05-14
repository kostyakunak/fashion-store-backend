package com.kounak.backend.controller;

import com.kounak.backend.exception.AuthException;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.User;
import com.kounak.backend.model.Wishlist;
import com.kounak.backend.model.Image;
import com.kounak.backend.model.Price;
import com.kounak.backend.service.WishlistService;
import com.kounak.backend.service.UserService;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.ImageService;
import com.kounak.backend.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/wishlist")
public class WishlistApiController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;
    private final ImageService imageService;
    private final PriceService priceService;

    public WishlistApiController(
            WishlistService wishlistService,
            UserService userService,
            ProductService productService,
            ImageService imageService,
            PriceService priceService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
        this.productService = productService;
        this.imageService = imageService;
        this.priceService = priceService;
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
     * Verify that the wishlist item belongs to the authenticated user
     */
    private void verifyWishlistOwnership(Long wishlistItemId) {
        User authenticatedUser = getAuthenticatedUser();
        Wishlist wishlist = wishlistService.getWishlistById(wishlistItemId);
        
        if (!wishlist.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AuthException("Not authorized to access this wishlist item");
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<Map<String, Object>>> getMyWishlist() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            Long userId = authenticatedUser.getId();
            
            // Get wishlist items for the user
            List<Wishlist> wishlistItems = wishlistService.getUserWishlist(userId);
            
            // Transform wishlist to a frontend-friendly format
            List<Map<String, Object>> enrichedWishlist = new ArrayList<>();
            
            for (Wishlist item : wishlistItems) {
                Product product = item.getProduct();
                List<Image> images = imageService.getImagesByProductId(product.getId());
                Price price = priceService.getLatestPriceByProductId(product.getId());
                
                Map<String, Object> enrichedItem = new HashMap<>();
                enrichedItem.put("id", item.getId());
                enrichedItem.put("userId", userId);
                enrichedItem.put("productId", product.getId());
                enrichedItem.put("name", product.getName());
                enrichedItem.put("price", price != null ? price.getOriginalPrice() : 0);
                enrichedItem.put("images", images);
                
                enrichedWishlist.add(enrichedItem);
            }
            
            return ResponseEntity.ok(enrichedWishlist);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody Map<String, Object> request) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            Long productId = Long.parseLong(request.get("productId").toString());
            
            // Check product existence
            Product product = productService.getProductById(productId);
            
            // Check if product already exists in wishlist
            List<Wishlist> existingItems = wishlistService.getUserWishlistByProductId(
                authenticatedUser.getId(), productId);
            
            if (!existingItems.isEmpty()) {
                // If product is already in wishlist, return it
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Product already added to wishlist",
                    "item", existingItems.get(0)
                ));
            }
            
            // Create new wishlist entry
            Wishlist wishlistItem = new Wishlist();
            wishlistItem.setUser(authenticatedUser);
            wishlistItem.setProduct(product);
            
            // Save the entry
            Wishlist savedItem = wishlistService.addToWishlist(wishlistItem);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Product added to wishlist",
                "item", savedItem
            ));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long wishlistItemId) {
        try {
            verifyWishlistOwnership(wishlistItemId);
            wishlistService.removeFromWishlist(wishlistItemId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Product removed from wishlist"
            ));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable Long productId) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<Wishlist> items = wishlistService.getUserWishlistByProductId(
                authenticatedUser.getId(), productId);
                
            if (items.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Product not found in wishlist"
                ));
            }
            
            for (Wishlist item : items) {
                wishlistService.removeFromWishlist(item.getId());
            }
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Product removed from wishlist"
            ));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}
package com.kounak.backend.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/wishlist")
public class PublicWishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;
    private final ImageService imageService;
    private final PriceService priceService;

    public PublicWishlistController(
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUserWishlist(@PathVariable Long userId) {
        try {
            // Получаем список избранного для пользователя
            List<Wishlist> wishlistItems = wishlistService.getUserWishlist(userId);
            
            // Преобразуем список избранного в формат, понятный фронтенду
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            Long productId = Long.parseLong(request.get("productId").toString());
            
            // Проверяем существование пользователя и продукта
            User user = userService.getUserById(userId);
            Product product = productService.getProductById(productId);
            
            // Проверяем, есть ли уже такой товар в избранном
            List<Wishlist> existingItems = wishlistService.getUserWishlistByProductId(userId, productId);
            
            if (!existingItems.isEmpty()) {
                // Если товар уже в избранном, возвращаем его
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Товар уже добавлен в избранное",
                    "item", existingItems.get(0)
                ));
            }
            
            // Создаем новую запись в избранном
            Wishlist wishlistItem = new Wishlist();
            wishlistItem.setUser(user);
            wishlistItem.setProduct(product);
            
            // Сохраняем запись
            Wishlist savedItem = wishlistService.addToWishlist(wishlistItem);
            
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Товар добавлен в избранное",
                "item", savedItem
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
            wishlistService.removeFromWishlist(wishlistItemId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Товар удален из избранного"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        try {
            List<Wishlist> items = wishlistService.getUserWishlistByProductId(userId, productId);
            if (items.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Товар не найден в избранном"
                ));
            }
            
            for (Wishlist item : items) {
                wishlistService.removeFromWishlist(item.getId());
            }
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Товар удален из избранного"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
} 
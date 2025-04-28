package com.kounak.backend.controller;

import com.kounak.backend.model.Cart;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.User;
import com.kounak.backend.service.CartService;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import com.kounak.backend.exception.ResourceNotFoundException;
import com.kounak.backend.exception.CartOperationException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = Logger.getLogger(CartController.class.getName());

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCart() {
        logger.info("=== НАЧАЛО getCart ===");
        try {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.info("Получен email пользователя из контекста безопасности: " + userEmail);
            
            try {
                List<Cart> cartItems = cartService.getCartByUserId(userEmail);
                logger.info("Получено элементов корзины: " + cartItems.size());
                return ResponseEntity.ok(cartItems);
            } catch (Exception e) {
                logger.severe("Ошибка при получении корзины: " + e.getMessage());
                logger.severe("Тип исключения: " + e.getClass().getName());
                logger.severe("Стек вызовов:");
                for (StackTraceElement element : e.getStackTrace()) {
                    logger.severe("\t" + element.toString());
                }
                throw e;
            }
        } catch (Exception e) {
            logger.severe("Общая ошибка в getCart: " + e.getMessage());
            logger.severe("Тип исключения: " + e.getClass().getName());
            logger.severe("Стек вызовов:");
            for (StackTraceElement element : e.getStackTrace()) {
                logger.severe("\t" + element.toString());
            }
            throw e;
        } finally {
            logger.info("=== ЗАВЕРШЕНИЕ getCart ===");
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getUserCart(@PathVariable Long userId) {
        logger.info("=== НАЧАЛО getUserCart для userId=" + userId + " ===");
        try {
            logger.info("Поиск пользователя по ID: " + userId);
            User user = userService.getUserById(userId);
            
            if (user == null) {
                logger.warning("Пользователь с ID " + userId + " не найден");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
            }
            
            logger.info("Пользователь найден: ID=" + user.getId() +
                       ", email=" + user.getEmail() +
                       ", имя=" + user.getFirstName() +
                       ", фамилия=" + user.getLastName());
            
            logger.info("Запрос корзины для email=" + user.getEmail());
            List<Cart> cartItems = cartService.getCartByUserId(user.getEmail());
            logger.info("Получено элементов корзины: " + cartItems.size());
            
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            logger.severe("Ошибка при получении корзины пользователя: " + e.getMessage());
            logger.severe("Тип исключения: " + e.getClass().getName());
            logger.severe("Стек вызовов:");
            for (StackTraceElement element : e.getStackTrace()) {
                logger.severe("\t" + element.toString());
            }
            throw e;
        } finally {
            logger.info("=== ЗАВЕРШЕНИЕ getUserCart для userId=" + userId + " ===");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long productId,
            @RequestParam Long sizeId,
            @RequestParam int quantity) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cartItem = cartService.addToCart(userEmail, productId, sizeId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCartItem(
            @PathVariable Long id,
            @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCartItem(id, cart);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(
            @RequestParam Long productId,
            @RequestParam Long sizeId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(userEmail);
        cartService.removeFromCart(user.getId(), productId, sizeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        logger.info("=== НАЧАЛО ОБРАБОТКИ ЗАПРОСА НА ДОБАВЛЕНИЕ В КОРЗИНУ ===");
        logger.info("Полученные данные: " + request);
        
        try {
            // Получаем email пользователя из контекста аутентификации
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByEmail(email);
            
            if (user == null) {
                logger.warning("Пользователь с email " + email + " не найден");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пользователь не найден"));
            }
            
            // Извлекаем и парсим параметры
            Long productId = Long.parseLong(request.get("productId").toString());
            Long sizeId = Long.parseLong(request.get("sizeId").toString());
            int quantity = Integer.parseInt(request.get("quantity").toString());
            
            logger.info(String.format("Параметры: userId=%d, productId=%d, sizeId=%d, quantity=%d",
                    user.getId(), productId, sizeId, quantity));
            
            // Проверяем существование товара
            logger.info("Проверка существования товара с ID: " + productId);
            Product product = productService.getProductById(productId);
            if (product == null) {
                logger.warning("Товар с ID " + productId + " не найден");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Товар с ID " + productId + " не найден"));
            }
            logger.info("Товар найден: " + product.getName());
            
            // Используем метод с проверками и валидацией
            logger.info("Добавление товара в корзину...");
            Cart savedItem = cartService.addItemToCart(user.getId(), productId, sizeId, quantity);
            logger.info("Товар успешно добавлен в корзину: " + savedItem.getId());
            
            logger.info("=== ЗАВЕРШЕНИЕ ОБРАБОТКИ ЗАПРОСА НА ДОБАВЛЕНИЕ В КОРЗИНУ (УСПЕХ) ===");
            return ResponseEntity.ok(savedItem);
            
        } catch (IllegalArgumentException e) {
            logger.severe("Ошибка валидации параметров: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Неверные параметры: " + e.getMessage()));
        } catch (ResourceNotFoundException e) {
            logger.severe("Ресурс не найден: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (CartOperationException e) {
            logger.severe("Ошибка операции с корзиной: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.severe("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Произошла ошибка: " + e.getMessage()));
        }
    }

    @PostMapping("/migrate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> migrateCart(@RequestBody Map<String, Object> request) {
        try {
            logger.info("=== НАЧАЛО МИГРАЦИИ КОРЗИНЫ ===");
            logger.info("Полученные данные: " + request);
            
            Long userId = Long.parseLong(request.get("userId").toString());
            List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
            
            // Проверяем, что пользователь мигрирует свою корзину
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userService.getUserByEmail(email);
            
            logger.info("Аутентифицированный пользователь: " + email + ", ID: " + currentUser.getId());
            logger.info("Запрошенный userId для миграции: " + userId);
            
            if (!currentUser.getId().equals(userId)) {
                logger.warning("Попытка миграции корзины другого пользователя. Аутентифицирован: " +
                              currentUser.getId() + ", запрошен: " + userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Нет доступа к этой операции"));
            }
            
            List<Cart> migratedItems = new ArrayList<>();
            
            // Обрабатываем каждый товар
            for (Map<String, Object> item : items) {
                Long productId = Long.parseLong(item.get("productId").toString());
                Long sizeId = Long.parseLong(item.get("sizeId").toString());
                int quantity = Integer.parseInt(item.get("quantity").toString());
                
                logger.info("Обработка товара: productId=" + productId + ", sizeId=" + sizeId +
                           ", quantity=" + quantity);
                
                // Получаем объект продукта
                Product product;
                try {
                    product = productService.getProductById(productId);
                    if (product == null) {
                        logger.warning("Товар с ID " + productId + " не найден, пропускаем");
                        continue; // Пропускаем товары, которых нет в базе
                    }
                } catch (Exception e) {
                    logger.warning("Ошибка при получении товара с ID " + productId + ": " + e.getMessage());
                    continue; // Пропускаем товары, с которыми возникли ошибки
                }
                
                // Проверяем, есть ли уже такой товар в корзине
                List<Cart> existingItems = cartService.getCartByUserIdAndProductIdAndSizeId(
                    userId, productId, sizeId);
                
                if (!existingItems.isEmpty()) {
                    // Если товар уже есть, обновляем количество
                    Cart existingItem = existingItems.get(0);
                    logger.info("Товар уже есть в корзине (ID: " + existingItem.getId() +
                               "), обновляем количество с " + existingItem.getQuantity() +
                               " на " + (existingItem.getQuantity() + quantity));
                    
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    Cart updatedItem = cartService.updateCartItem(existingItem.getId(), existingItem);
                    migratedItems.add(updatedItem);
                } else {
                    // Если товара нет, создаем новый элемент корзины
                    logger.info("Товара нет в корзине, создаем новый элемент");
                    
                    Cart cartItem = new Cart();
                    cartItem.setUser(currentUser);
                    cartItem.setProduct(product);
                    cartItem.setSizeId(sizeId);
                    cartItem.setQuantity(quantity);
                    
                    Cart savedItem = cartService.addToCart(cartItem);
                    logger.info("Товар добавлен в корзину, ID: " + savedItem.getId());
                    migratedItems.add(savedItem);
                }
            }
            
            logger.info("Миграция завершена, добавлено/обновлено товаров: " + migratedItems.size());
            logger.info("=== ЗАВЕРШЕНИЕ МИГРАЦИИ КОРЗИНЫ ===");
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "migratedItems", migratedItems.size()
            ));
        } catch (Exception e) {
            logger.severe("Ошибка при миграции корзины: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
        try {
            // Получаем email текущего пользователя
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByEmail(email);
            
            if (user == null) {
                logger.warning("Пользователь с email " + email + " не найден");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пользователь не найден"));
            }
            
            // Получаем элемент корзины
            Cart cartItem = cartService.getCartItemById(cartItemId);
            
            // Проверяем принадлежность элемента корзины пользователю
            if (!cartItem.getUser().getId().equals(user.getId())) {
                logger.warning("Попытка удаления чужого товара из корзины. ID элемента: " + cartItemId + 
                             ", пользователь: " + email);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Нет доступа к этому элементу корзины"));
            }
            
            // Удаляем элемент корзины
            cartService.removeFromCart(cartItemId);
            logger.info("Удален элемент корзины с ID " + cartItemId + " пользователем " + email);
            return ResponseEntity.ok(Map.of("success", true));
            
        } catch (ResourceNotFoundException e) {
            logger.warning("Элемент корзины не найден: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (CartOperationException e) {
            logger.warning("Ошибка при удалении из корзины: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.severe("Неожиданная ошибка при удалении из корзины: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Произошла ошибка: " + e.getMessage()));
        }
    }

    @DeleteMapping("/item")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeItemFromCart(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            Long productId = Long.parseLong(request.get("productId").toString());
            Long sizeId = Long.parseLong(request.get("sizeId").toString());
            Integer quantity = request.get("quantity") != null 
                ? Integer.parseInt(request.get("quantity").toString()) 
                : 0;
            
            // Проверяем, что пользователь удаляет из своей корзины
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userService.getUserByEmail(email);
            
            if (!currentUser.getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Нет доступа к этой операции"));
            }
            
            boolean result = cartService.removeItemFromCart(userId, productId, sizeId, quantity);
            
            if (result) {
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Товар не найден в корзине"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Неверные параметры: " + e.getMessage()));
        } catch (CartOperationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Произошла ошибка: " + e.getMessage()));
        }
    }

    @PutMapping("/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long cartItemId, 
            @RequestBody Map<String, Integer> request) {
        try {
            // Проверяем принадлежность элемента корзины
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByEmail(email);
            Cart cartItem = cartService.getCartItemById(cartItemId);
            
            if (!cartItem.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Нет доступа к этому элементу корзины"));
            }
            
            int quantity = request.get("quantity");
            if (quantity <= 0) {
                cartService.removeFromCart(cartItemId);
                return ResponseEntity.ok(Map.of("success", true, "removed", true));
            } else {
                cartItem.setQuantity(quantity);
                Cart updatedCart = cartService.updateCartItem(cartItemId, cartItem);
                return ResponseEntity.ok(updatedCart);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{cartItemId}/size")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCartItemSize(
            @PathVariable Long cartItemId, 
            @RequestBody Map<String, Long> request) {
        try {
            // Проверяем принадлежность элемента корзины
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByEmail(email);
            Cart cartItem = cartService.getCartItemById(cartItemId);
            
            if (!cartItem.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Нет доступа к этому элементу корзины"));
            }
            
            Long sizeId = request.get("sizeId");
            cartItem.setSizeId(sizeId);
            Cart updatedCart = cartService.updateCartItem(cartItemId, cartItem);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
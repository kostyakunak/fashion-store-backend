package com.kounak.backend.service;

import com.kounak.backend.exception.CartException;
import com.kounak.backend.model.Cart;
import com.kounak.backend.model.User;
import com.kounak.backend.model.Warehouse;
import com.kounak.backend.repository.CartRepository;
import com.kounak.backend.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CartService {
    private static final Logger logger = Logger.getLogger(CartService.class.getName());
    
    private final CartRepository cartRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartService(CartRepository cartRepository, 
                      WarehouseRepository warehouseRepository,
                      ProductService productService,
                      UserService userService) {
        this.cartRepository = cartRepository;
        this.warehouseRepository = warehouseRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional
    public Cart addToCart(String userEmail, Long productId, Long sizeId, int quantity) {
        logger.info("=== НАЧАЛО ДОБАВЛЕНИЯ ТОВАРА В КОРЗИНУ (SERVICE) ===");
        logger.info(String.format("Параметры: userEmail=%s, productId=%d, sizeId=%d, quantity=%d",
                userEmail, productId, sizeId, quantity));
        
        try {
            // Получаем пользователя по email
            logger.info("Поиск пользователя по email: " + userEmail);
            User user = userService.getUserByEmail(userEmail);
            if (user == null) {
                logger.severe("Пользователь с email " + userEmail + " не найден");
                throw new CartException(
                    "Пользователь не найден",
                    "USER_NOT_FOUND"
                );
            }
            logger.info("Пользователь найден: ID=" + user.getId());

            // Проверяем существование товара
            if (productService.getProductById(productId) == null) {
                throw new CartException(
                    "Товар не найден",
                    "PRODUCT_NOT_FOUND"
                );
            }

            // Проверяем наличие товара на складе
            Optional<Warehouse> warehouseItem = warehouseRepository
                .findByProductIdAndSizeId(productId, sizeId);

            if (warehouseItem.isEmpty()) {
                throw new CartException(
                    "Товар с выбранным размером недоступен",
                    "PRODUCT_SIZE_NOT_AVAILABLE"
                );
            }

            if (warehouseItem.get().getQuantity() < quantity) {
                throw new CartException(
                    "Недостаточно товара на складе",
                    "INSUFFICIENT_STOCK",
                    String.format("Запрошено: %d, Доступно: %d", 
                        quantity, warehouseItem.get().getQuantity())
                );
            }

            // Проверяем, есть ли уже такой товар в корзине
            logger.info("Проверка наличия товара в корзине пользователя");
            List<Cart> existingItems = cartRepository
                .findByUserIdAndProductIdAndSizeId(user.getId(), productId, sizeId);

            if (!existingItems.isEmpty()) {
                Cart existingItem = existingItems.get(0);
                int newQuantity = existingItem.getQuantity() + quantity;
                logger.info("Товар уже есть в корзине (ID: " + existingItem.getId() +
                           "), текущее количество: " + existingItem.getQuantity() +
                           ", новое количество: " + newQuantity);

                if (newQuantity > warehouseItem.get().getQuantity()) {
                    logger.warning("Превышено доступное количество товара. Запрошено: " +
                                  newQuantity + ", доступно: " + warehouseItem.get().getQuantity());
                    throw new CartException(
                        "Превышено доступное количество товара",
                        "QUANTITY_EXCEEDED",
                        String.format("Максимально доступно: %d",
                            warehouseItem.get().getQuantity())
                    );
                }

                existingItem.setQuantity(newQuantity);
                Cart updatedItem = cartRepository.save(existingItem);
                logger.info("Количество товара в корзине обновлено, ID: " + updatedItem.getId());
                return updatedItem;
            }
            
            logger.info("Товара нет в корзине, создаем новый элемент");

            // Создаем новый элемент корзины
            Cart cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(productService.getProductById(productId));
            cartItem.setSizeId(sizeId);
            cartItem.setQuantity(quantity);

            Cart savedItem = cartRepository.save(cartItem);
            logger.info("Новый товар добавлен в корзину, ID: " + savedItem.getId());
            return savedItem;
        } catch (CartException e) {
            logger.severe("Ошибка при добавлении товара в корзину: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Неожиданная ошибка при добавлении товара в корзину: " + e.getMessage());
            throw new CartException(
                "Ошибка при добавлении товара в корзину",
                "CART_OPERATION_ERROR",
                e.getMessage()
            );
        } finally {
            logger.info("=== ЗАВЕРШЕНИЕ ДОБАВЛЕНИЯ ТОВАРА В КОРЗИНУ (SERVICE) ===");
        }
    }

    // Temporarily removing cache to debug
    // @Cacheable(value = "user-cart", key = "#userEmail")
    public List<Cart> getCartByUserId(String userEmail) {
        logger.info("=== НАЧАЛО getCartByUserId для " + userEmail + " ===");
        try {
            logger.info("Поиск пользователя по email: " + userEmail);
            
            // Проверяем, существует ли пользователь с таким email
            try {
                User user = userService.getUserByEmail(userEmail);
                
                if (user == null) {
                    logger.severe("Пользователь с email " + userEmail + " не найден");
                    throw new CartException("Пользователь не найден", "USER_NOT_FOUND");
                }
                
                logger.info("Пользователь найден: ID=" + user.getId() +
                           ", email=" + user.getEmail() +
                           ", имя=" + user.getFirstName() +
                           ", фамилия=" + user.getLastName());
                
                // Проверяем, активен ли пользователь
                logger.info("Статус пользователя: " + (user.isEnabled() ? "активен" : "неактивен"));
                
                logger.info("Запрос корзины для userId=" + user.getId());
                try {
                    List<Cart> cartItems = cartRepository.findByUserId(user.getId());
                    logger.info("Найдено элементов корзины: " + cartItems.size());
                    
                    // Логируем детали каждого элемента корзины
                    if (!cartItems.isEmpty()) {
                        logger.info("Детали элементов корзины:");
                        for (Cart item : cartItems) {
                            logger.info("CartItem ID=" + item.getId() +
                                       ", ProductID=" + (item.getProduct() != null ? item.getProduct().getId() : "null") +
                                       ", SizeID=" + item.getSizeId() +
                                       ", Quantity=" + item.getQuantity());
                        }
                    }
                    
                    return cartItems;
                } catch (Exception e) {
                    logger.severe("Ошибка при запросе корзины из репозитория: " + e.getMessage());
                    logger.severe("Тип исключения: " + e.getClass().getName());
                    logger.severe("Стек вызовов:");
                    for (StackTraceElement element : e.getStackTrace()) {
                        logger.severe("\t" + element.toString());
                    }
                    throw e;
                }
            } catch (Exception e) {
                logger.severe("Ошибка при получении пользователя: " + e.getMessage());
                logger.severe("Тип исключения: " + e.getClass().getName());
                logger.severe("Стек вызовов:");
                for (StackTraceElement element : e.getStackTrace()) {
                    logger.severe("\t" + element.toString());
                }
                throw e;
            }
        } catch (Exception e) {
            logger.severe("Общая ошибка при получении корзины: " + e.getMessage());
            logger.severe("Тип исключения: " + e.getClass().getName());
            logger.severe("Стек вызовов:");
            for (StackTraceElement element : e.getStackTrace()) {
                logger.severe("\t" + element.toString());
            }
            throw e;
        } finally {
            logger.info("=== ЗАВЕРШЕНИЕ getCartByUserId для " + userEmail + " ===");
        }
    }

    @Transactional
    public Cart updateCartItem(Long id, Cart cart) {
        Cart existingCart = getCartItemById(id);
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getUserByEmail(currentUserEmail);
        
        if (!existingCart.getUser().getId().equals(currentUser.getId())) {
            throw new CartException(
                "Нет доступа к этому элементу корзины",
                "ACCESS_DENIED"
            );
        }

        // Проверяем наличие товара на складе
        Optional<Warehouse> warehouseItem = warehouseRepository
            .findByProductIdAndSizeId(
                existingCart.getProduct().getId(),
                existingCart.getSizeId()
            );

        if (warehouseItem.isEmpty() || warehouseItem.get().getQuantity() < cart.getQuantity()) {
            throw new CartException(
                "Недостаточно товара на складе",
                "INSUFFICIENT_STOCK"
            );
        }

        cart.setId(id);
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(Long userId, Long productId, Long sizeId) {
        List<Cart> items = cartRepository
            .findByUserIdAndProductIdAndSizeId(userId, productId, sizeId);

        if (items.isEmpty()) {
            throw new CartException(
                "Товар не найден в корзине",
                "ITEM_NOT_FOUND"
            );
        }

        cartRepository.deleteAll(items);
    }

    public Cart getCartItemById(Long id) {
        return cartRepository.findById(id)
            .orElseThrow(() -> new CartException(
                "Элемент корзины не найден",
                "CART_ITEM_NOT_FOUND"
            ));
    }
    /**
     * Adds an item to the cart with the specified parameters
     */
    @Transactional
    public Cart addItemToCart(Long userId, Long productId, Long sizeId, int quantity) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new CartException("Пользователь не найден", "USER_NOT_FOUND");
        }
        
        // Проверяем существование товара
        if (productService.getProductById(productId) == null) {
            throw new CartException("Товар не найден", "PRODUCT_NOT_FOUND");
        }

        // Проверяем наличие товара на складе
        Optional<Warehouse> warehouseItem = warehouseRepository
            .findByProductIdAndSizeId(productId, sizeId);

        if (warehouseItem.isEmpty()) {
            throw new CartException(
                "Товар с выбранным размером недоступен",
                "PRODUCT_SIZE_NOT_AVAILABLE"
            );
        }

        if (warehouseItem.get().getQuantity() < quantity) {
            throw new CartException(
                "Недостаточно товара на складе",
                "INSUFFICIENT_STOCK",
                String.format("Запрошено: %d, Доступно: %d",
                    quantity, warehouseItem.get().getQuantity())
            );
        }

        // Проверяем, есть ли уже такой товар в корзине
        List<Cart> existingItems = cartRepository
            .findByUserIdAndProductIdAndSizeId(userId, productId, sizeId);

        if (!existingItems.isEmpty()) {
            Cart existingItem = existingItems.get(0);
            int newQuantity = existingItem.getQuantity() + quantity;

            if (newQuantity > warehouseItem.get().getQuantity()) {
                throw new CartException(
                    "Превышено доступное количество товара",
                    "QUANTITY_EXCEEDED",
                    String.format("Максимально доступно: %d",
                        warehouseItem.get().getQuantity())
                );
            }

            existingItem.setQuantity(newQuantity);
            return cartRepository.save(existingItem);
        }

        // Создаем новый элемент корзины
        Cart cartItem = new Cart();
        cartItem.setUser(user);
        cartItem.setProduct(productService.getProductById(productId));
        cartItem.setSizeId(sizeId);
        cartItem.setQuantity(quantity);

        return cartRepository.save(cartItem);
    }
    
    /**
     * Adds a cart item directly
     */
    @Transactional
    public Cart addToCart(Cart cartItem) {
        // Проверяем наличие товара на складе
        Optional<Warehouse> warehouseItem = warehouseRepository
            .findByProductIdAndSizeId(
                cartItem.getProduct().getId(),
                cartItem.getSizeId()
            );

        if (warehouseItem.isEmpty() || warehouseItem.get().getQuantity() < cartItem.getQuantity()) {
            throw new CartException(
                "Недостаточно товара на складе",
                "INSUFFICIENT_STOCK"
            );
        }

        return cartRepository.save(cartItem);
    }
    
    /**
     * Gets cart items by user, product and size IDs
     */
    public List<Cart> getCartByUserIdAndProductIdAndSizeId(Long userId, Long productId, Long sizeId) {
        return cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId);
    }
    
    /**
     * Removes an item from cart by cart item ID
     */
    @Transactional
    public void removeFromCart(Long cartItemId) {
        if (!cartRepository.existsById(cartItemId)) {
            throw new CartException(
                "Элемент корзины не найден",
                "CART_ITEM_NOT_FOUND"
            );
        }
        cartRepository.deleteById(cartItemId);
    }
    
    /**
     * Removes items from cart with option to reduce quantity
     */
    @Transactional
    public boolean removeItemFromCart(Long userId, Long productId, Long sizeId, Integer quantity) {
        List<Cart> items = cartRepository
            .findByUserIdAndProductIdAndSizeId(userId, productId, sizeId);

        if (items.isEmpty()) {
            return false;
        }

        Cart item = items.get(0);
        
        if (quantity == null || quantity <= 0 || quantity >= item.getQuantity()) {
            // Удаляем товар полностью
            cartRepository.delete(item);
        } else {
            // Уменьшаем количество
            item.setQuantity(item.getQuantity() - quantity);
            cartRepository.save(item);
        }
        
        return true;
    }
}
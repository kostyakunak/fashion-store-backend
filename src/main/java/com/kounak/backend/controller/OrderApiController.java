package com.kounak.backend.controller;

import com.kounak.backend.exception.AuthException;
import com.kounak.backend.model.Order;
import com.kounak.backend.model.OrderDetails;
import com.kounak.backend.model.User;
import com.kounak.backend.service.OrderService;
import com.kounak.backend.service.UserService;
import com.kounak.backend.service.ImageService;
import com.kounak.backend.model.Image;
import com.kounak.backend.service.WarehouseService;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.Size;
import com.kounak.backend.model.Address;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.SizeService;
import com.kounak.backend.service.AddressService;
import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderApiController {

    private final OrderService orderService;
    private final UserService userService;
    private final ImageService imageService;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final SizeService sizeService;
    private final AddressService addressService;

    public OrderApiController(OrderService orderService, UserService userService, ImageService imageService, WarehouseService warehouseService, ProductService productService, SizeService sizeService, AddressService addressService) {
        this.orderService = orderService;
        this.userService = userService;
        this.imageService = imageService;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.sizeService = sizeService;
        this.addressService = addressService;
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
     * Verify that the order belongs to the authenticated user
     */
    private void verifyOrderOwnership(Long orderId) {
        User authenticatedUser = getAuthenticatedUser();
        Order order = orderService.getOrderById(orderId);
        
        if (!order.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AuthException("Not authorized to access this order");
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders() {
        System.out.println(">>> getMyOrders called");
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<Order> orders = orderService.getOrdersByUserId(authenticatedUser.getId());
            // enrich products with mainImageUrl
            for (Order order : orders) {
                if (order.getItems() != null) {
                    for (OrderDetails item : order.getItems()) {
                        if (item.getProduct() != null) {
                            Long productId = item.getProduct().getId();
                            List<Image> images = imageService.getImagesByProductId(productId);
                            String mainImageUrl = null;
                            if (images != null && !images.isEmpty()) {
                                // Сначала ищем isMain, потом sortOrder=0, иначе первое
                                mainImageUrl = images.stream().filter(img -> Boolean.TRUE.equals(img.getIsMain())).map(Image::getImageUrl).findFirst()
                                        .orElse(images.stream().filter(img -> img.getSortOrder() != null && img.getSortOrder() == 0).map(Image::getImageUrl).findFirst()
                                        .orElse(images.get(0).getImageUrl()));
                            }
                            // enrich product with mainImageUrl (через map, чтобы не ломать сериализацию)
                            if (mainImageUrl != null) {
                                // Костыль: enrich product через reflection-like map
                                Map<String, Object> productMap = new HashMap<>();
                                productMap.put("id", item.getProduct().getId());
                                productMap.put("name", item.getProduct().getName());
                                productMap.put("mainImageUrl", mainImageUrl);
                                // Можно добавить другие поля по необходимости
                                item.setProductMap(productMap); // потребуется добавить setProductMap в OrderDetails
                            }
                        }
                    }
                }
            }
            return ResponseEntity.ok(orders);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching orders: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            verifyOrderOwnership(orderId);
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching order: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        try {
            verifyOrderOwnership(orderId);
            List<OrderDetails> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching order details: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> payload) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            
            // 1. Получаем адрес доставки (addressId)
            Long addressId = payload.get("addressId") != null ? Long.valueOf(payload.get("addressId").toString()) : null;
            Address address = null;
            if (addressId != null) {
                address = addressService.getAddressById(addressId).orElse(null);
                if (address == null) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Адрес не найден"));
                }
            }

            // 2. Получаем способ оплаты (paymentMethod)
            String paymentMethod = payload.get("paymentMethod") != null ? payload.get("paymentMethod").toString() : null;
            // (можно сохранить в заказе, если нужно)

            // 3. Получаем список товаров (items)
            List<Map<String, Object>> itemsRaw = (List<Map<String, Object>>) payload.get("items");
            if (itemsRaw == null || itemsRaw.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Корзина пуста"));
            }

            // 4. Проверяем остатки и формируем OrderDetails
            List<OrderDetails> orderDetailsList = new java.util.ArrayList<>();
            for (Map<String, Object> item : itemsRaw) {
                Long productId = Long.valueOf(item.get("productId").toString());
                Long sizeId = Long.valueOf(item.get("sizeId").toString());
                int quantity = Integer.parseInt(item.get("quantity").toString());
                BigDecimal price = new BigDecimal(item.get("price").toString());

                // Проверяем остаток
                int stock = warehouseService.getProductQuantityBySize(productId, sizeId);
                if (stock < quantity) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "error", "Недостаточно товара на складе",
                        "productId", productId,
                        "sizeId", sizeId,
                        "available", stock,
                        "requested", quantity
                    ));
                }
            }
            // 5. Списываем остатки и формируем OrderDetails
            for (Map<String, Object> item : itemsRaw) {
                Long productId = Long.valueOf(item.get("productId").toString());
                Long sizeId = Long.valueOf(item.get("sizeId").toString());
                int quantity = Integer.parseInt(item.get("quantity").toString());
                BigDecimal price = new BigDecimal(item.get("price").toString());

                boolean decremented = warehouseService.decrementProductQuantity(productId, sizeId, quantity);
                if (!decremented) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "error", "Не удалось списать остаток (возможно, товар уже разобрали)",
                        "productId", productId,
                        "sizeId", sizeId
                    ));
                }
                Product product = productService.getProductById(productId);
                Size size = sizeService.getSizeById(sizeId);
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setProduct(product);
                orderDetails.setSize(size);
                orderDetails.setQuantity(quantity);
                orderDetails.setPriceAtPurchase(price);
                orderDetailsList.add(orderDetails);
            }

            // 6. Создаём заказ
            Order order = new Order();
            order.setUser(authenticatedUser);
            order.setAddress(address);
            // Можно сохранить paymentMethod, если есть поле
            // order.setPaymentMethod(paymentMethod);
            Order savedOrder = orderService.createOrder(order, orderDetailsList);
            return ResponseEntity.ok(savedOrder);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating order: " + e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            verifyOrderOwnership(orderId);
            Order order = orderService.getOrderById(orderId);
            
            // Устанавливаем статус на CANCELLED
            order.setStatus(OrderStatus.CANCELLED);
            
            Order updatedOrder = orderService.updateOrder(order);
            return ResponseEntity.ok(updatedOrder);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error cancelling order: " + e.getMessage()));
        }
    }
}
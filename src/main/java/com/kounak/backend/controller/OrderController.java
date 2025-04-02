package com.kounak.backend.controller;

import com.kounak.backend.model.*;
import com.kounak.backend.service.OrderService;
import com.kounak.backend.service.UserService;
import com.kounak.backend.service.ProductService;
import com.kounak.backend.service.SizeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final SizeService sizeService;

    public OrderController(OrderService orderService, UserService userService,
                           ProductService productService, SizeService sizeService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.sizeService = sizeService;
    }

    // ✅ Создание заказа с деталями
    @PostMapping
    public Order createOrder(@RequestBody Map<String, Object> payload) {
        System.out.println("Получен запрос на создание заказа: " + payload);

        Object userIdObj = payload.get("userId");
        if (userIdObj == null) throw new RuntimeException("userId is missing");

        Long userId = Long.valueOf(userIdObj.toString());
        User user = userService.getUserById(userId);
        System.out.println("Пользователь найден: " + user.getId());

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.AWAITING_PAYMENT);

        // Проверяем, есть ли товары в заказе
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) payload.get("items");
        if (itemsList == null || itemsList.isEmpty()) {
            throw new RuntimeException("Список товаров пуст");
        }

        List<OrderDetails> items = new ArrayList<>();
        for (Map<String, Object> item : itemsList) {
            OrderDetails orderDetails = new OrderDetails();

            Long productId = Long.valueOf(item.get("productId").toString());
            Long sizeId = Long.valueOf(item.get("sizeId").toString());

            Product product = productService.getProductById(productId);
            Size size = sizeService.getSizeById(sizeId);

            orderDetails.setProduct(product);
            orderDetails.setSize(size);
            orderDetails.setQuantity(Integer.parseInt(item.get("quantity").toString()));
            items.add(orderDetails);
        }

        System.out.println("Создаем заказ для пользователя: " + user.getId() + " с " + items.size() + " товарами");

        Order savedOrder = orderService.createOrder(order, items);
        System.out.println("Заказ успешно создан: " + savedOrder.getId());

        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return orderService.updateOrder(id, payload.get("status"));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
package com.kounak.backend.controller;

import com.kounak.backend.dto.OrderDetailsDTO;
import com.kounak.backend.model.OrderDetails;
import com.kounak.backend.repository.OrderDetailsRepository;
import com.kounak.backend.repository.OrderRepository;
import com.kounak.backend.repository.ProductRepository;
import com.kounak.backend.repository.SizeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/order-details")
public class OrderDetailsController {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

    @Autowired
    public OrderDetailsController(OrderDetailsRepository orderDetailsRepository,
                                OrderRepository orderRepository,
                                ProductRepository productRepository,
                                SizeRepository sizeRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrderDetails() {
        try {
            List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
            logger.info("Fetched order details: {}", orderDetails);
            for (OrderDetails detail : orderDetails) {
                logger.info("Order detail: id={}, order={}, product={}, size={}", 
                    detail.getId(), 
                    detail.getOrder() != null ? detail.getOrder().getId() : "null",
                    detail.getProduct() != null ? detail.getProduct().getId() : "null",
                    detail.getSize() != null ? detail.getSize().getId() : "null");
            }
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            logger.error("Error fetching order details: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching order details: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@RequestBody Map<String, Object> payload) {
        try {
            OrderDetails orderDetail = new OrderDetails();
            
            // Устанавливаем ID
            Long id = Long.valueOf(payload.get("id").toString());
            orderDetail.setId(id);
            
            // Устанавливаем заказ
            Long orderId = Long.valueOf(payload.get("orderId").toString());
            orderDetail.setOrder(orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found")));
            
            // Устанавливаем товар
            Long productId = Long.valueOf(payload.get("productId").toString());
            orderDetail.setProduct(productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found")));
            
            // Устанавливаем размер
            Long sizeId = Long.valueOf(payload.get("sizeId").toString());
            orderDetail.setSize(sizeRepository.findById(sizeId)
                .orElseThrow(() -> new RuntimeException("Size not found")));
            
            // Устанавливаем количество
            orderDetail.setQuantity(Integer.parseInt(payload.get("quantity").toString()));
            
            // Устанавливаем цену
            orderDetail.setPriceAtPurchase(new java.math.BigDecimal(payload.get("priceAtPurchase").toString()));
            
            OrderDetails savedOrderDetail = orderDetailsRepository.save(orderDetail);
            return ResponseEntity.ok(savedOrderDetail);
            
        } catch (Exception e) {
            logger.error("Error creating order detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating order detail: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailsDTO orderDetailsDTO) {
        try {
            OrderDetails existingOrderDetail = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order detail not found"));
            
            // Проверяем, изменился ли orderId
            if (!existingOrderDetail.getOrder().getId().equals(orderDetailsDTO.getOrderId())) {
                // Если orderId изменился, удаляем старую запись и создаем новую
                orderDetailsRepository.deleteById(id);
                
                OrderDetails newOrderDetail = new OrderDetails();
                newOrderDetail.setOrder(orderRepository.findById(orderDetailsDTO.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found")));
                newOrderDetail.setProduct(productRepository.findById(orderDetailsDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found")));
                newOrderDetail.setQuantity(orderDetailsDTO.getQuantity());
                newOrderDetail.setPriceAtPurchase(orderDetailsDTO.getPrice());
                
                OrderDetails savedOrderDetail = orderDetailsRepository.save(newOrderDetail);
                return ResponseEntity.ok(savedOrderDetail);
            } else {
                // Если orderId не изменился, просто обновляем существующую запись
                existingOrderDetail.setQuantity(orderDetailsDTO.getQuantity());
                existingOrderDetail.setPriceAtPurchase(orderDetailsDTO.getPrice());
                
                OrderDetails updatedOrderDetail = orderDetailsRepository.save(existingOrderDetail);
                return ResponseEntity.ok(updatedOrderDetail);
            }
        } catch (Exception e) {
            logger.error("Error updating order detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        try {
            orderDetailsRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting order detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting order detail: " + e.getMessage());
        }
    }
} 
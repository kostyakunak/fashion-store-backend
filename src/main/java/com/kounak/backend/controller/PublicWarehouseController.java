package com.kounak.backend.controller;

import com.kounak.backend.model.Size;
import com.kounak.backend.model.Warehouse;
import com.kounak.backend.service.SizeService;
import com.kounak.backend.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/warehouse")
public class PublicWarehouseController {

    private static final Logger logger = LoggerFactory.getLogger(PublicWarehouseController.class);
    
    private final WarehouseService warehouseService;
    private final SizeService sizeService;

    public PublicWarehouseController(WarehouseService warehouseService, SizeService sizeService) {
        this.warehouseService = warehouseService;
        this.sizeService = sizeService;
    }

    @GetMapping("/product/{productId}/sizes")
    public ResponseEntity<List<Map<String, Object>>> getAvailableSizesForProduct(@PathVariable Long productId) {
        logger.info("Получение размеров для товара с ID: {}", productId);
        
        // Получаем все записи со склада для данного товара
        List<Warehouse> warehouseItems = warehouseService.getAvailableWarehouseItemsByProductId(productId);
        
        logger.info("Найдено {} записей на складе для товара {}", warehouseItems.size(), productId);
        
        // Создаем расширенные объекты размеров с информацией о наличии
        List<Map<String, Object>> sizesWithAvailability = warehouseItems.stream()
                .map(item -> {
                    Size size = item.getSize();
                    int quantity = item.getQuantity();
                    boolean inStock = quantity > 0;
                    
                    Map<String, Object> sizeInfo = new HashMap<>();
                    sizeInfo.put("id", size.getId());
                    sizeInfo.put("name", size.getName());
                    sizeInfo.put("quantity", quantity);
                    sizeInfo.put("inStock", inStock);
                    
                    logger.debug("Размер: {}, ID: {}, quantity: {}, inStock: {}", 
                            size.getName(), size.getId(), quantity, inStock);
                    return sizeInfo;
                })
                .collect(Collectors.toList());
        
        // Добавим логирование результата
        logger.info("Отправка списка из {} размеров для товара {}", sizesWithAvailability.size(), productId);
        
        return ResponseEntity.ok(sizesWithAvailability);
    }
    
    // Новый эндпоинт для проверки наличия товара определенного размера
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkProductAvailability(
            @RequestParam Long productId, 
            @RequestParam Long sizeId) {
        
        logger.info("Проверка наличия товара: productId={}, sizeId={}", productId, sizeId);
        
        int quantity = warehouseService.getProductQuantityBySize(productId, sizeId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("available", quantity > 0);
        result.put("quantity", quantity);
        
        logger.info("Результат проверки: available={}, quantity={}", quantity > 0, quantity);
        
        return ResponseEntity.ok(result);
    }
} 
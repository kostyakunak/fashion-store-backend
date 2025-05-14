package com.kounak.backend.controller;

import com.kounak.backend.model.Size;
import com.kounak.backend.model.Warehouse;
import com.kounak.backend.service.SizeService;
import com.kounak.backend.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/warehouse")
public class PublicWarehouseController {

    private final WarehouseService warehouseService;
    private final SizeService sizeService;

    public PublicWarehouseController(WarehouseService warehouseService, SizeService sizeService) {
        this.warehouseService = warehouseService;
        this.sizeService = sizeService;
    }

    @GetMapping("/product/{productId}/sizes")
    public ResponseEntity<List<Map<String, Object>>> getAvailableSizesForProduct(@PathVariable Long productId) {
        // Получаем все записи со склада для данного товара (независимо от количества)
        List<Warehouse> warehouseItems = warehouseService.getAllWarehouseItemsByProductId(productId);
        
        // Создаем расширенные объекты размеров с информацией о наличии
        List<Map<String, Object>> sizesWithAvailability = warehouseItems.stream()
                .map(item -> {
                    Size size = item.getSize();
                    Map<String, Object> sizeInfo = new HashMap<>();
                    sizeInfo.put("id", size.getId());
                    sizeInfo.put("name", size.getName());
                    sizeInfo.put("quantity", item.getQuantity());
                    sizeInfo.put("inStock", item.getQuantity() > 0);
                    return sizeInfo;
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(sizesWithAvailability);
    }
} 
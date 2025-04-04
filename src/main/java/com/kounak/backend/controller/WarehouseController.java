package com.kounak.backend.controller;

import com.kounak.backend.model.Warehouse;
import com.kounak.backend.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<?> addWarehouseEntry(@RequestBody Map<String, Object> request) {
        logger.info("Adding new warehouse entry with data: {}", request);
        try {
            Long id = Long.parseLong(request.get("id").toString());
            Long productId = Long.parseLong(request.get("productId").toString());
            Long sizeId = Long.parseLong(request.get("sizeId").toString());
            Integer quantity = Integer.parseInt(request.get("quantity").toString());

            Warehouse result = warehouseService.addWarehouseEntry(id, productId, sizeId, quantity);
            logger.info("Successfully added warehouse entry with ID: {}", id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error adding warehouse entry: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка при добавлении записи: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Warehouse> getAllWarehouseEntries() {
        logger.info("Getting all warehouse entries");
        List<Warehouse> entries = warehouseService.getAllWarehouseEntries();
        logger.info("Found {} entries", entries.size());
        return entries;
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouseEntry(@PathVariable Long id) {
        warehouseService.deleteWarehouseEntry(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWarehouseEntry(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        logger.info("Updating warehouse entry with ID: {}, data: {}", id, request);
        try {
            Long newId = Long.parseLong(request.get("id").toString());
            Long productId = Long.parseLong(request.get("productId").toString());
            Long sizeId = Long.parseLong(request.get("sizeId").toString());
            Integer quantity = Integer.parseInt(request.get("quantity").toString());

            Warehouse result = warehouseService.updateWarehouseEntry(id, newId, productId, sizeId, quantity);
            logger.info("Successfully updated warehouse entry with ID: {} to new ID: {}", id, newId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error updating warehouse entry: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка при обновлении записи: " + e.getMessage());
        }
    }
}
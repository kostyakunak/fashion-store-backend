package com.kounak.backend.controller;

import com.kounak.backend.model.Warehouse;
import com.kounak.backend.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public Warehouse addWarehouseEntry(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouseEntry(warehouse);
    }

    @GetMapping
    public List<Warehouse> getAllWarehouseEntries() {
        return warehouseService.getAllWarehouseEntries();
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouseEntry(@PathVariable Long id) {
        warehouseService.deleteWarehouseEntry(id);
    }
}
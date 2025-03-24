package com.kounak.backend.service;

import com.kounak.backend.model.Warehouse;
import com.kounak.backend.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse addWarehouseEntry(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouseEntries() {
        return warehouseRepository.findAll();
    }

    public void deleteWarehouseEntry(Long id) {
        warehouseRepository.deleteById(id);
    }
}
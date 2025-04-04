package com.kounak.backend.service;

import com.kounak.backend.model.Warehouse;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.Size;
import com.kounak.backend.repository.WarehouseRepository;
import com.kounak.backend.repository.ProductRepository;
import com.kounak.backend.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository,
                          ProductRepository productRepository,
                          SizeRepository sizeRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    public boolean existsById(Long id) {
        return warehouseRepository.existsById(id);
    }

    public Warehouse addWarehouseEntry(Long id, Long productId, Long sizeId, Integer quantity) {
        if (existsById(id)) {
            throw new RuntimeException("Запись с ID " + id + " уже существует");
        }

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        Size size = sizeRepository.findById(sizeId)
            .orElseThrow(() -> new RuntimeException("Size not found"));

        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setProduct(product);
        warehouse.setSize(size);
        warehouse.setQuantity(quantity);

        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouseEntries() {
        return warehouseRepository.findAll();
    }

    public void deleteWarehouseEntry(Long id) {
        warehouseRepository.deleteById(id);
    }

    public Warehouse updateWarehouseEntry(Long oldId, Long newId, Long productId, Long sizeId, Integer quantity) {
        // Проверяем существование старой записи
        Warehouse warehouse = warehouseRepository.findById(oldId)
            .orElseThrow(() -> new RuntimeException("Warehouse entry not found"));
        
        // Если ID изменился, проверяем, что новый ID не занят
        if (!oldId.equals(newId) && existsById(newId)) {
            throw new RuntimeException("Запись с ID " + newId + " уже существует");
        }
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        Size size = sizeRepository.findById(sizeId)
            .orElseThrow(() -> new RuntimeException("Size not found"));

        // Если ID изменился, удаляем старую запись и создаем новую
        if (!oldId.equals(newId)) {
            warehouseRepository.deleteById(oldId);
            Warehouse newWarehouse = new Warehouse();
            newWarehouse.setId(newId);
            newWarehouse.setProduct(product);
            newWarehouse.setSize(size);
            newWarehouse.setQuantity(quantity);
            return warehouseRepository.save(newWarehouse);
        } else {
            // Если ID не изменился, просто обновляем существующую запись
            warehouse.setProduct(product);
            warehouse.setSize(size);
            warehouse.setQuantity(quantity);
            return warehouseRepository.save(warehouse);
        }
    }
}
package com.kounak.backend.controller;

import com.kounak.backend.model.Price;
import com.kounak.backend.service.PriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public Price addPrice(@RequestBody Price price) {
        return priceService.addPrice(price);
    }

    @GetMapping
    public List<Price> getAllPrices() {
        return priceService.getAllPrices();
    }

    @DeleteMapping("/{id}")
    public void deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
    }
}
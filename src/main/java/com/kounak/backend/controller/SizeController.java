package com.kounak.backend.controller;

import com.kounak.backend.model.Size;
import com.kounak.backend.service.SizeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sizes")
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @PostMapping
    public Size addSize(@RequestBody Size size) {
        return sizeService.addSize(size);
    }

    @GetMapping
    public List<Size> getAllSizes() {
        return sizeService.getAllSizes();
    }

    @PutMapping("/{id}")
    public Size updateSize(@PathVariable Long id, @RequestBody Size size) {
        return sizeService.updateSize(id, size);
    }

    @DeleteMapping("/{id}")
    public void deleteSize(@PathVariable Long id) {
        sizeService.deleteSize(id);
    }
}
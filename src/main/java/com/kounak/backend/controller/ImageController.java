package com.kounak.backend.controller;

import com.kounak.backend.model.Image;
import com.kounak.backend.service.ImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public Image addImage(@RequestBody Image image) {
        return imageService.addImage(image);
    }

    @GetMapping("/{productId}")
    public List<Image> getImagesByProduct(@PathVariable Long productId) {
        return imageService.getImagesByProduct(productId);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }
}
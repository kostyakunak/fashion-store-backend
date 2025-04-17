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

    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    @PostMapping
    public Image addImage(@RequestBody Image image) {
        return imageService.addImage(image);
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable Long id, @RequestBody Image image) {
        return imageService.updateImage(id, image);
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
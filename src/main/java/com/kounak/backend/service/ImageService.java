package com.kounak.backend.service;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import com.kounak.backend.repository.ImageRepository;
import com.kounak.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getImagesByProduct(Long productId) {
        return imageRepository.findByProductId(productId); // Запрос теперь использует productId
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
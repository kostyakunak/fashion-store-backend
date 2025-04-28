package com.kounak.backend.service;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import com.kounak.backend.repository.ImageRepository;
import com.kounak.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ImageService {

    private static final Logger logger = Logger.getLogger(ImageService.class.getName());

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(Long id, Image image) {
        Image existingImage = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Изображение не найдено с ID: " + id));

        // Обновляем поля
        existingImage.setProduct(image.getProduct());
        existingImage.setImageUrl(image.getImageUrl());
        existingImage.setIsMain(image.getIsMain());
        existingImage.setSortOrder(image.getSortOrder());

        return imageRepository.save(existingImage);
    }

    public List<Image> getImagesByProduct(Long productId) {
        return imageRepository.findByProductId(productId);
    }

    public List<Image> getImagesByProductId(Long productId) {
        logger.info("Fetching images for product ID: " + productId);
        List<Image> images = imageRepository.findByProductId(productId);
        logger.info("Found " + images.size() + " images for product ID: " + productId);
        return images;
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
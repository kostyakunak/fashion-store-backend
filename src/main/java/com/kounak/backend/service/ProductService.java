package com.kounak.backend.service;

import com.kounak.backend.model.Category;
import com.kounak.backend.model.OrderDetails;
import com.kounak.backend.model.Product;
import com.kounak.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final PriceRepository priceRepository;
    private final WarehouseRepository warehouseRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public ProductService(
            ProductRepository productRepository, 
            CategoryRepository categoryRepository,
            ImageRepository imageRepository,
            PriceRepository priceRepository,
            WarehouseRepository warehouseRepository,
            CartRepository cartRepository,
            WishlistRepository wishlistRepository,
            OrderDetailsRepository orderDetailsRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.priceRepository = priceRepository;
        this.warehouseRepository = warehouseRepository;
        this.cartRepository = cartRepository;
        this.wishlistRepository = wishlistRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product addProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new RuntimeException("Product name is required");
        }
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new RuntimeException("Category is required");
        }

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getName() != null && !product.getName().trim().isEmpty()) {
            existingProduct.setName(product.getName());
        }
        if (product.getProductDetails() != null) {
            existingProduct.setProductDetails(product.getProductDetails());
        }
        if (product.getMeasurements() != null) {
            existingProduct.setMeasurements(product.getMeasurements());
        }
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Новый комплексный метод для полного удаления товара и связанных с ним данных
     * @param id ID товара для удаления
     * @param forceDelete Принудительное удаление, даже если товар есть в заказах
     * @return true если удаление выполнено успешно
     */
    @Transactional
    public boolean deleteProductWithRelatedEntities(Long id, boolean forceDelete) {
        // Проверяем существование товара
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар с ID " + id + " не найден"));
        
        // Проверяем, используется ли товар в заказах
        List<OrderDetails> existingOrders = orderDetailsRepository.findByProductId(id);
        if (!existingOrders.isEmpty() && !forceDelete) {
            throw new RuntimeException("Нельзя удалить товар с ID " + id + ", так как он присутствует в " 
                    + existingOrders.size() + " заказах. Используйте forceDelete=true для принудительного удаления.");
        }
        
        // Удаляем связанные данные в правильном порядке
        try {
            // 1. Удаляем из корзин
            cartRepository.deleteByProductId(id);
            
            // 2. Удаляем из списков желаний
            wishlistRepository.deleteByProductId(id);
            
            // 3. Удаляем из деталей заказов (если forceDelete=true)
            if (forceDelete && !existingOrders.isEmpty()) {
                orderDetailsRepository.deleteByProductId(id);
            }
            
            // 4. Удаляем записи склада
            warehouseRepository.deleteByProductId(id);
            
            // 5. Удаляем цены
            priceRepository.deleteByProductId(id);
            
            // 6. Удаляем изображения
            imageRepository.deleteByProductId(id);
            
            // 7. Удаляем сам товар
            productRepository.delete(product);
            
            return true;
        } catch (Exception e) {
            // Если возникла ошибка, транзакция автоматически откатится благодаря @Transactional
            throw new RuntimeException("Ошибка при удалении товара: " + e.getMessage(), e);
        }
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return productRepository.findByCategory(category);
    }
}
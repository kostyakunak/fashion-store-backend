package com.kounak.backend;

// REST API
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.kounak.backend.Product;
import com.kounak.backend.ProductRepository;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getCatalog(@RequestParam(required = false, defaultValue = "0") int start,
                                    @RequestParam(required = false, defaultValue = "10") int count) {
        List<Product> products = productRepository.findAll();
        return products.subList(start, Math.min(start + count, products.size()));
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
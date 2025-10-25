package com.kounak.backend.service;

import com.kounak.backend.model.Image;
import com.kounak.backend.model.Product;
import com.kounak.backend.repository.ImageRepository;
import com.kounak.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ImageReplacementService {

    private final RestTemplate restTemplate;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Value("${pexels.api.key:CW8HQ5nbbrmlsTbbpLrLeaO7pViNrClewuwupeMUXrOjTLowVtEZTOO4}")
    private String pexelsApiKey;

    private static final String PEXELS_API_URL = "https://api.pexels.com/v1/search";
    private static final Pattern INVALID_URL_PATTERN = Pattern.compile(
        ".*\\.(jpg|jpeg|png|gif|webp)$",
        Pattern.CASE_INSENSITIVE
    );

    private static final Map<String, String> FASHION_SEARCH_TERMS = new HashMap<>();
    static {
        FASHION_SEARCH_TERMS.put("belt", "leather belt fashion accessory");
        FASHION_SEARCH_TERMS.put("bag", "fashion handbag luxury");
        FASHION_SEARCH_TERMS.put("dress", "elegant dress fashion clothing");
        FASHION_SEARCH_TERMS.put("shirt", "fashion shirt clothing");
        FASHION_SEARCH_TERMS.put("pants", "fashion pants trousers clothing");
        FASHION_SEARCH_TERMS.put("shoes", "fashion footwear shoes");
        FASHION_SEARCH_TERMS.put("jewelry", "fashion jewelry accessories");
        FASHION_SEARCH_TERMS.put("watch", "luxury watch fashion accessory");
        FASHION_SEARCH_TERMS.put("hat", "fashion hat clothing accessory");
        FASHION_SEARCH_TERMS.put("scarf", "fashion scarf clothing accessory");
        FASHION_SEARCH_TERMS.put("jacket", "fashion jacket clothing");
        FASHION_SEARCH_TERMS.put("coat", "fashion coat clothing");
        FASHION_SEARCH_TERMS.put("skirt", "fashion skirt clothing");
        FASHION_SEARCH_TERMS.put("blouse", "fashion blouse clothing");
        FASHION_SEARCH_TERMS.put("sweater", "fashion sweater clothing");
        FASHION_SEARCH_TERMS.put("jeans", "fashion jeans denim clothing");
        FASHION_SEARCH_TERMS.put("suit", "fashion suit clothing");
        FASHION_SEARCH_TERMS.put("tie", "fashion tie accessory");
        FASHION_SEARCH_TERMS.put("boots", "fashion boots footwear");
        FASHION_SEARCH_TERMS.put("sandals", "fashion sandals footwear");
        FASHION_SEARCH_TERMS.put("sneakers", "fashion sneakers footwear");
        FASHION_SEARCH_TERMS.put("heels", "fashion heels footwear");
        FASHION_SEARCH_TERMS.put("accessory", "fashion accessory");
        FASHION_SEARCH_TERMS.put("clothing", "fashion clothing apparel");
    }

    @Autowired
    public ImageReplacementService(ImageRepository imageRepository,
                                  ProductRepository productRepository) {
        this.restTemplate = new RestTemplate();
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    /**
     * Identifies products with invalid or inappropriate images
     */
    public List<Product> findProductsNeedingImageReplacement() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .filter(this::needsImageReplacement)
                .collect(Collectors.toList());
    }

    private boolean needsImageReplacement(Product product) {
        List<Image> images = imageRepository.findByProductId(product.getId());
        if (images.isEmpty()) {
            return true;
        }

        // Check if any image URL is invalid or inappropriate
        return images.stream().anyMatch(image ->
            isUrlInvalid(image.getImageUrl()) ||
            isImageInappropriate(image.getImageUrl(), product.getName())
        );
    }

    private boolean isUrlInvalid(String url) {
        if (url == null || url.trim().isEmpty()) {
            return true;
        }

        // Basic URL validation
        try {
            new java.net.URL(url);
        } catch (Exception e) {
            return true;
        }

        // Check if it's likely a generic/inappropriate URL
        String lowerUrl = url.toLowerCase();
        return lowerUrl.contains("placeholder") ||
               lowerUrl.contains("via.placeholder") ||
               lowerUrl.contains("example.com") ||
               !INVALID_URL_PATTERN.matcher(url).matches();
    }

    private boolean isImageInappropriate(String url, String productName) {
        if (url == null || productName == null) {
            return false;
        }

        String lowerUrl = url.toLowerCase();
        String lowerProductName = productName.toLowerCase();

        // Simple heuristic: if product is belt but URL contains face/woman/man, might be inappropriate
        if (lowerProductName.contains("belt") || lowerProductName.contains("ремень")) {
            return lowerUrl.contains("face") ||
                   lowerUrl.contains("woman") ||
                   lowerUrl.contains("man") ||
                   lowerUrl.contains("person") ||
                   lowerUrl.contains("girl") ||
                   lowerUrl.contains("boy");
        }

        return false;
    }

    /**
     * Searches Pexels for appropriate images based on product information
     */
    public List<String> searchPexelsImages(String productName, String categoryName) {
        String searchQuery = generateSearchQuery(productName, categoryName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", pexelsApiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String url = PEXELS_API_URL + "?query=" + java.net.URLEncoder.encode(searchQuery, "UTF-8") +
                        "&per_page=10&orientation=portrait";

            ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> photos = (List<Map<String, Object>>) response.getBody().get("photos");
                if (photos != null) {
                    return photos.stream()
                            .map(photo -> {
                                Map<String, Object> src = (Map<String, Object>) photo.get("src");
                                return src != null ? (String) src.get("large") : null;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching Pexels: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private String generateSearchQuery(String productName, String categoryName) {
        if (productName == null) productName = "";
        if (categoryName == null) categoryName = "";

        String lowerProductName = productName.toLowerCase();
        String lowerCategoryName = categoryName.toLowerCase();

        // Check for specific keywords and map to search terms
        for (Map.Entry<String, String> entry : FASHION_SEARCH_TERMS.entrySet()) {
            if (lowerProductName.contains(entry.getKey()) || lowerCategoryName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Fallback: use product name with fashion context
        String baseQuery = productName + " fashion";
        return baseQuery.length() > 50 ? productName.substring(0, 45) + " fashion" : baseQuery;
    }

    /**
     * Replaces images for a specific product
     */
    public boolean replaceProductImages(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            return false;
        }

        Product product = productOpt.get();
        List<String> newImageUrls = searchPexelsImages(product.getName(), product.getCategory().getName());

        if (newImageUrls.isEmpty()) {
            return false;
        }

        try {
            // Delete existing images
            imageRepository.deleteByProductId(productId);

            // Add new images
            for (int i = 0; i < Math.min(newImageUrls.size(), 3); i++) {
                Image newImage = new Image();
                newImage.setProduct(product);
                newImage.setImageUrl(newImageUrls.get(i));
                newImage.setIsMain(i == 0); // First image is main
                newImage.setSortOrder(i);
                imageRepository.save(newImage);
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error replacing images for product " + productId + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Batch replaces images for multiple products
     */
    public Map<Long, Boolean> batchReplaceImages(List<Long> productIds) {
        Map<Long, Boolean> results = new HashMap<>();

        for (Long productId : productIds) {
            boolean success = replaceProductImages(productId);
            results.put(productId, success);

            // Small delay to avoid overwhelming Pexels API
            try {
                Thread.sleep(500); // Increased delay to be more respectful to API
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        return results;
    }

    /**
     * Validates if an image URL is accessible
     */
    public boolean validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return false;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Void> response = restTemplate.exchange(
                imageUrl, HttpMethod.HEAD, entity, Void.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enhanced image replacement with validation
     */
    public boolean replaceProductImagesWithValidation(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            return false;
        }

        Product product = productOpt.get();
        List<String> newImageUrls = searchPexelsImages(product.getName(), product.getCategory().getName());

        if (newImageUrls.isEmpty()) {
            return false;
        }

        // Validate URLs before proceeding
        List<String> validUrls = newImageUrls.stream()
                .filter(this::validateImageUrl)
                .collect(Collectors.toList());

        if (validUrls.isEmpty()) {
            return false;
        }

        try {
            // Delete existing images
            imageRepository.deleteByProductId(productId);

            // Add validated new images
            for (int i = 0; i < Math.min(validUrls.size(), 3); i++) {
                Image newImage = new Image();
                newImage.setProduct(product);
                newImage.setImageUrl(validUrls.get(i));
                newImage.setIsMain(i == 0); // First image is main
                newImage.setSortOrder(i);
                imageRepository.save(newImage);
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error replacing images for product " + productId + ": " + e.getMessage());
            return false;
        }
    }
}
package com.kounak.backend.service;

import com.kounak.backend.exception.CartOperationException;
import com.kounak.backend.exception.ResourceNotFoundException;
import com.kounak.backend.model.Cart;
import com.kounak.backend.model.Product;
import com.kounak.backend.model.User;
import com.kounak.backend.model.Warehouse;
import com.kounak.backend.model.Size;
import com.kounak.backend.repository.CartRepository;
import com.kounak.backend.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    
    @Mock
    private CartRepository cartRepository;
    
    @Mock
    private WarehouseRepository warehouseRepository;
    
    @Mock
    private ProductService productService;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private CartService cartService;
    
    private User testUser;
    private Product testProduct;
    private Warehouse testWarehouseItem;
    private Cart testCartItem;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        
        testProduct = new Product();
        testProduct.setId(2L);
        testProduct.setName("Тестовый товар");
        
        Size testSize = new Size();
        testSize.setId(3L);
        
        testWarehouseItem = new Warehouse();
        testWarehouseItem.setId(1L);
        testWarehouseItem.setProduct(testProduct);
        testWarehouseItem.setSize(testSize);
        testWarehouseItem.setQuantity(10);
        
        testCartItem = new Cart();
        testCartItem.setId(1L);
        testCartItem.setUser(testUser);
        testCartItem.setProduct(testProduct);
        testCartItem.setSizeId(3L);
        testCartItem.setQuantity(2);
    }
    
    @Test
    void addItemToCart_NewItem_Success() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 2;
        
        when(userService.getUserById(userId)).thenReturn(testUser);
        when(productService.getProductById(productId)).thenReturn(testProduct);
        when(warehouseRepository.findByProductIdAndSizeId(productId, sizeId))
            .thenReturn(Optional.of(testWarehouseItem));
        when(cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId))
            .thenReturn(new ArrayList<>());
        when(cartRepository.save(any(Cart.class))).thenReturn(testCartItem);
        
        // Act
        Cart result = cartService.addItemToCart(userId, productId, sizeId, quantity);
        
        // Assert
        assertNotNull(result);
        assertEquals(testCartItem, result);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
    
    @Test
    void addItemToCart_ExistingItem_Success() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 1;
        
        List<Cart> existingItems = new ArrayList<>();
        existingItems.add(testCartItem);
        
        when(userService.getUserById(userId)).thenReturn(testUser);
        when(productService.getProductById(productId)).thenReturn(testProduct);
        when(warehouseRepository.findByProductIdAndSizeId(productId, sizeId))
            .thenReturn(Optional.of(testWarehouseItem));
        when(cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId))
            .thenReturn(existingItems);
        when(cartRepository.save(any(Cart.class))).thenReturn(testCartItem);
        
        // Act
        Cart result = cartService.addItemToCart(userId, productId, sizeId, quantity);
        
        // Assert
        assertNotNull(result);
        assertEquals(testCartItem, result);
        assertEquals(3, testCartItem.getQuantity()); // 2 + 1 = 3
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
    
    @Test
    void addItemToCart_UserNotFound_ThrowsException() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 2;
        
        when(userService.getUserById(userId)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> 
            cartService.addItemToCart(userId, productId, sizeId, quantity));
        
        verify(cartRepository, never()).save(any(Cart.class));
    }
    
    @Test
    void addItemToCart_ProductNotFound_ThrowsException() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 2;
        
        when(userService.getUserById(userId)).thenReturn(testUser);
        when(productService.getProductById(productId)).thenReturn(null);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> 
            cartService.addItemToCart(userId, productId, sizeId, quantity));
        
        verify(cartRepository, never()).save(any(Cart.class));
    }
    
    @Test
    void addItemToCart_OutOfStock_ThrowsException() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 20; // Больше чем на складе (10)
        
        when(userService.getUserById(userId)).thenReturn(testUser);
        when(productService.getProductById(productId)).thenReturn(testProduct);
        when(warehouseRepository.findByProductIdAndSizeId(productId, sizeId))
            .thenReturn(Optional.of(testWarehouseItem));
        
        // Act & Assert
        assertThrows(CartOperationException.class, () -> 
            cartService.addItemToCart(userId, productId, sizeId, quantity));
        
        verify(cartRepository, never()).save(any(Cart.class));
    }
    
    @Test
    void removeItemFromCart_Success() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 0; // Удаляем полностью
        
        List<Cart> existingItems = new ArrayList<>();
        existingItems.add(testCartItem);
        
        when(cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId))
            .thenReturn(existingItems);
        
        // Act
        boolean result = cartService.removeItemFromCart(userId, productId, sizeId, quantity);
        
        // Assert
        assertTrue(result);
        verify(cartRepository, times(1)).deleteById(anyLong());
    }
    
    @Test
    void removeItemFromCart_DecreaseQuantity_Success() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 1; // Уменьшаем количество на 1
        
        testCartItem.setQuantity(3); // Изначально было 3
        
        List<Cart> existingItems = new ArrayList<>();
        existingItems.add(testCartItem);
        
        when(cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId))
            .thenReturn(existingItems);
        when(cartRepository.save(any(Cart.class))).thenReturn(testCartItem);
        
        // Act
        boolean result = cartService.removeItemFromCart(userId, productId, sizeId, quantity);
        
        // Assert
        assertTrue(result);
        assertEquals(2, testCartItem.getQuantity()); // 3 - 1 = 2
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(cartRepository, never()).deleteById(anyLong());
    }
    
    @Test
    void removeItemFromCart_ItemNotFound_ReturnsFalse() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Long sizeId = 3L;
        int quantity = 0;
        
        when(cartRepository.findByUserIdAndProductIdAndSizeId(userId, productId, sizeId))
            .thenReturn(new ArrayList<>());
        
        // Act
        boolean result = cartService.removeItemFromCart(userId, productId, sizeId, quantity);
        
        // Assert
        assertFalse(result);
        verify(cartRepository, never()).deleteById(anyLong());
        verify(cartRepository, never()).save(any(Cart.class));
    }
} 
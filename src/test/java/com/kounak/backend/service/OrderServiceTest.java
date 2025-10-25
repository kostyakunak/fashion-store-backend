package com.kounak.backend.service;

import com.kounak.backend.model.Order;
import com.kounak.backend.model.OrderDetails;
import com.kounak.backend.model.User;
import com.kounak.backend.repository.OrderDetailsRepository;
import com.kounak.backend.repository.OrderRepository;
import com.kounak.backend.repository.PriceRepository;
import com.kounak.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailsRepository orderDetailsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PriceRepository priceRepository;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_success() {
        User user = new User();
        user.setId(1L);
        Order order = new Order();
        order.setUser(user);
        List<OrderDetails> items = new ArrayList<>();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderDetailsRepository.findByOrderId(any(Long.class))).thenReturn(new ArrayList<>());

        Order result = orderService.createOrder(order, items);
        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(orderRepository, atLeastOnce()).save(any(Order.class));
    }

    @Test
    void createOrder_noUser_throwsException() {
        Order order = new Order();
        order.setUser(null);
        List<OrderDetails> items = new ArrayList<>();
        Exception ex = assertThrows(RuntimeException.class, () -> orderService.createOrder(order, items));
        assertTrue(ex.getMessage().contains("User is required"));
    }

    @Test
    void createOrder_userNotFound_throwsException() {
        User user = new User();
        user.setId(2L);
        Order order = new Order();
        order.setUser(user);
        List<OrderDetails> items = new ArrayList<>();
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(RuntimeException.class, () -> orderService.createOrder(order, items));
        assertTrue(ex.getMessage().contains("User not found"));
    }
} 
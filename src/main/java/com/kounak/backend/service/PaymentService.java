package com.kounak.backend.service;

import com.kounak.backend.model.Order;
import com.kounak.backend.model.Payment;
import com.kounak.backend.repository.OrderRepository;
import com.kounak.backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository; // Добавляем OrderRepository

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository; // Инициализируем
    }

    public Payment addPayment(Payment payment) {
        if (payment.getOrder() == null || payment.getOrder().getId() == null) {
            throw new RuntimeException("Order must be set before saving payment.");
        }

        // Загружаем заказ из базы, чтобы избежать transient-объектов
        Order order = orderRepository.findById(payment.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setOrder(order);

        payment.setUser(order.getUser());

        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
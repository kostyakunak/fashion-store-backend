package com.kounak.backend.service;

import com.kounak.backend.model.Order;
import com.kounak.backend.model.Payment;
import com.kounak.backend.repository.OrderRepository;
import com.kounak.backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    
    public Payment updatePayment(Long id, Payment payment) {
        Optional<Payment> existingPayment = paymentRepository.findById(id);
        if (existingPayment.isPresent()) {
            // Сохраняем ID
            payment.setId(id);
            
            // Если указан заказ, проверяем его существование
            if (payment.getOrder() != null && payment.getOrder().getId() != null) {
                Order order = orderRepository.findById(payment.getOrder().getId())
                        .orElseThrow(() -> new RuntimeException("Order not found"));
                payment.setOrder(order);
                
                // Если пользователь не указан или изменен, обновляем его из заказа
                if (payment.getUser() == null || !payment.getUser().getId().equals(order.getUser().getId())) {
                    payment.setUser(order.getUser());
                }
            } else {
                // Если заказ не указан, сохраняем текущий заказ
                Payment currentPayment = existingPayment.get();
                payment.setOrder(currentPayment.getOrder());
                payment.setUser(currentPayment.getUser());
            }
            
            // Если дата не указана, сохраняем текущую
            if (payment.getPaymentDate() == null) {
                payment.setPaymentDate(existingPayment.get().getPaymentDate());
            }
            
            return paymentRepository.save(payment);
        }
        throw new RuntimeException("Payment not found");
    }
}
package com.kounak.backend.model;

public enum OrderStatus {
    AWAITING_PAYMENT, // Новый статус: заказ создан, но не оплачен
    PENDING,         // Заказ подтвержден, но не отправлен
    SHIPPED,         // Заказ отправлен
    DELIVERED,       // Заказ доставлен
    CANCELLED        // Заказ отменен
}
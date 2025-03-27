package com.kounak.backend.dto;

import com.kounak.backend.model.Order;
import com.kounak.backend.model.OrderDetails;
import java.util.List;

public class OrderRequest {
    private Order order;
    private List<OrderDetails> items;

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public List<OrderDetails> getItems() { return items; }
    public void setItems(List<OrderDetails> items) { this.items = items; }
}
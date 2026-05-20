package com.kanjih.toptal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Order {

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("customer_id")
    private int customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("loyalty_points")
    private int loyaltyPoints;

    @JsonProperty("order_items")
    private List<OrderItem> orderItems;

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", customerName='" + customerName
                + "', loyaltyPoints=" + loyaltyPoints + ", items=" + orderItems + '}';
    }
}
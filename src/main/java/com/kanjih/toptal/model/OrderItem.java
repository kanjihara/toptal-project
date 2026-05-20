package com.kanjih.toptal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_category")
    private String productCategory;

    @JsonProperty("unit_price")
    private double unitPrice;

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return "OrderItem{productId=" + productId + ", productName='" + productName
                + "', category='" + productCategory + "', unitPrice=" + unitPrice + '}';
    }
}
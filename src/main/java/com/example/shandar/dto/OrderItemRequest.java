package com.example.shandar.dto;

public class OrderItemRequest {
    private Long id; // This is the Menu Item ID
    private Integer qty;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
}
package com.example.shandar.dto;
import java.util.List;

public class OrderRequest {
    private Integer tableNumber;
    private List<OrderItemRequest> items;

    public Integer getTableNumber() { return tableNumber; }
    public void setTableNumber(Integer tableNumber) { this.tableNumber = tableNumber; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
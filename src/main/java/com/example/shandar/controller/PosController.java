package com.example.shandar.controller;

import com.example.shandar.dto.OrderRequest;
import com.example.shandar.dto.OrderItemRequest;
import com.example.shandar.model.CustomerOrder;
import com.example.shandar.model.MenuItem;
import com.example.shandar.model.OrderItem;
import com.example.shandar.repository.CustomerOrderRepository;
import com.example.shandar.repository.MenuItemRepository;
import com.example.shandar.repository.OrderItemRepository;
import com.example.shandar.service.KotPrinterService; // Import the new service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PosController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private KotPrinterService kotPrinterService; // Inject the printer service

    @GetMapping("/menu")
    public List<MenuItem> getMenu() {
        return menuItemRepository.findAll();
    }

    @PostMapping("/menu")
    public MenuItem addMenuItem(@RequestBody MenuItem item) {
        return menuItemRepository.save(item);
    }

    @PostMapping("/order")
    public String placeOrder(@RequestBody OrderRequest request) {

        CustomerOrder order = new CustomerOrder();
        order.setTableNumber(request.getTableNumber());
        order.setStatus("OPEN");
        order = customerOrderRepository.save(order);

        List<OrderItem> savedItems = new ArrayList<>(); // Keep track of items to print

        for (OrderItemRequest itemReq : request.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(order);

            MenuItem menuItem = menuItemRepository.findById(itemReq.getId()).orElse(null);
            orderItem.setMenuItem(menuItem);

            orderItem.setQuantity(itemReq.getQty());
            orderItem.setKotPrinted(true); // Mark as printed because we are doing it now

            savedItems.add(orderItemRepository.save(orderItem));
        }

        // --- NEW: Trigger the printer! ---
        kotPrinterService.printKot(order, savedItems);

        return "Order successfully saved to database for Table " + request.getTableNumber();
    }
}
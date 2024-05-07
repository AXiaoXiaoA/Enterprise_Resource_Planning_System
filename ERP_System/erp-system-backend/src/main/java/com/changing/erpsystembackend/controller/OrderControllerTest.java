package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.entity.Order;
import com.changing.erpsystembackend.service.OrderServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderControllerTest {
    private final OrderServiceTest orderService;

    @Autowired
    public OrderControllerTest(OrderServiceTest orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public void createOrder() {
        orderService.createOrder();
    }

    @GetMapping("/read")
    public Order getOrderByOrderNumber() {
        return orderService.getOrderByOrderNumber();
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/update")
    public void updateOrder() {
        orderService.updateOrder();
    }

    @GetMapping("/delete")
    public void deleteOrder() {
        orderService.deleteOrderByOrderNumber();
    }
}

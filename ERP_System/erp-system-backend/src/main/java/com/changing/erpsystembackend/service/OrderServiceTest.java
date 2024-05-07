package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.Order;
import com.changing.erpsystembackend.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceTest {

    private final OrderMapper mapper;

    @Autowired
    public OrderServiceTest(OrderMapper mapper) {
        this.mapper = mapper;
    }

    public void createOrder() {
        Order expectedOrder = new Order();
        expectedOrder.setOrderNumber(123);
        expectedOrder.setDepartment("IT");
        expectedOrder.setCreatorEmployeeId(456);
        expectedOrder.setCreationDate(new Date());
        expectedOrder.setProductName("Product A");
        expectedOrder.setSpecification("Spec A");
        expectedOrder.setMaterial("Material A");
        expectedOrder.setQuantity(10);
        expectedOrder.setUnitPrice(BigDecimal.valueOf(100.0));
        expectedOrder.setClient("Client X");
        expectedOrder.setContractFilePath("/path/to/contract.pdf");
        mapper.createOrder(expectedOrder);

        Order expectedOrder1 = new Order();
        expectedOrder1.setOrderNumber(456);
        expectedOrder1.setDepartment("HR");
        expectedOrder1.setCreatorEmployeeId(456);
        expectedOrder1.setCreationDate(new Date());
        expectedOrder1.setProductName("Product B");
        expectedOrder1.setSpecification("Spec B");
        expectedOrder1.setMaterial("Material B");
        expectedOrder1.setQuantity(10);
        expectedOrder1.setUnitPrice(BigDecimal.valueOf(100.0));
        expectedOrder1.setClient("Client Y");
        expectedOrder1.setContractFilePath("/path/to/contract.pdf");
        mapper.createOrder(expectedOrder1);
    }

    public Order getOrderByOrderNumber() {
        long orderNumber = 123;
        return mapper.findOrderByOrderNumber(orderNumber);
    }

    public List<Order> getAllOrders() {
        return mapper.findAll();
    }

    public void updateOrder() {
        Order order = new Order();
        order.setOrderNumber(123);
        order.setDepartment("HR");
        order.setCreatorEmployeeId(456);
        order.setCreationDate(new Date());
        order.setProductName("Product A");
        order.setSpecification("Spec A");
        order.setMaterial("Material A");
        order.setQuantity(10);
        order.setUnitPrice(BigDecimal.valueOf(100.0));
        order.setClient("Client X");
        order.setContractFilePath("/path/to/contract.pdf");

        mapper.updateOrder(order);
    }

    public void deleteOrderByOrderNumber() {
        long orderNumber = 123;
        mapper.deleteByOrderNumber(orderNumber);
    }
}

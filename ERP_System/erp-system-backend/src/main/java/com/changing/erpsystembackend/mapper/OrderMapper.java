package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void createOrder(Order order);

    Order findOrderByOrderNumber(long orderNumber);
    Order findOrderByDepartment(String department);
    Order findOrderByCreatorEmployeeId(long creatorEmployeeId);
    Order findOrderByCreationDate(java.util.Date creationDate);
    Order findOrderByProductName(String productName);
    Order findOrderByMaterial(String material);
    Order findOrderByClient(String client);
    java.util.List<Order> findAll();

    void deleteByOrderNumber(long orderNumber);

    void updateOrder(Order order);
}
package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long orderNumber;
    private String department;
    private long creatorEmployeeId;
    private java.util.Date creationDate;

    private String productName;
    private String specification;
    private String material;
    private int quantity;
    private java.math.BigDecimal unitPrice;

    private String client;
    private String contractFilePath;
}

package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {
    private Long id;

    private Long productId;
    private Product product;

    private Long salesEmployeeId;
    private Employee salesEmployee;

    private Long companyId;
    private Company company;

    private Long repoEmployeeId;
    private Employee repoEmployee;

    private Integer quantity;
    private byte[] contract;
    private java.sql.Date applyDate;
    private String destination;
    private String status;
    private String description;
}

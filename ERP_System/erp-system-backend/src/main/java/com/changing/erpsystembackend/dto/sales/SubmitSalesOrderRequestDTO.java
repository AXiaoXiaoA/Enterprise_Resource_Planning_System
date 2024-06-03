package com.changing.erpsystembackend.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitSalesOrderRequestDTO {
    private Long salesEmployeeId;
    private String productName;
    private Double price;
    private int quantity;
    private String companyName;
    private Long companyTel;
    private String destination;
    private String description;
    private String contractData;
    private String status;
}
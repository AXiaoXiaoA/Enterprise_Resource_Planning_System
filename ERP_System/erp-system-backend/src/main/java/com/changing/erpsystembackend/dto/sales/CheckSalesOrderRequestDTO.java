package com.changing.erpsystembackend.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSalesOrderRequestDTO {
    private Long id;
    private String status;
}
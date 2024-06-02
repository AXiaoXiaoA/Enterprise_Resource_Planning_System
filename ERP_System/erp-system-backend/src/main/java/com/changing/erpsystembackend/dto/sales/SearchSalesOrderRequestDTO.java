package com.changing.erpsystembackend.dto.sales;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchSalesOrderRequestDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String productName;
    private String companyName;
    private String destination;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
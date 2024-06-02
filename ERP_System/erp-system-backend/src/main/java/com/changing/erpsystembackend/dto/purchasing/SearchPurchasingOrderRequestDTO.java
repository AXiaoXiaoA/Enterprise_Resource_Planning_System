package com.changing.erpsystembackend.dto.purchasing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPurchasingOrderRequestDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String materialName;
    private String companyName;
    private String departure;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
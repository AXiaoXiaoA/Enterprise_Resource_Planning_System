package com.changing.erpsystembackend.dto.purchasing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitPurchasingOrderRequestDTO {
    private Long purchasingEmployeeId;
    private String materialName;
    private int quantity;
    private String companyName;
    private Long companyTel;
    private String departure;
    private String description;
    private String contractData;
    private String status;
}
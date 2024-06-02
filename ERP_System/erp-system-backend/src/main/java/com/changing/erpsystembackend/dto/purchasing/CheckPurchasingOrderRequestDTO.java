package com.changing.erpsystembackend.dto.purchasing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPurchasingOrderRequestDTO {
    private Long id;
    private String status;
}
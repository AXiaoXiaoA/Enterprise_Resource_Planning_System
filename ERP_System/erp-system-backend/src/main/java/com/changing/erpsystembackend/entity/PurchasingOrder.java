package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasingOrder {
    private Long id;
    private Long materialId;
    private Long purchasingEmployeeId;
    private Long companyId;
    private Long repoEmployeeId;
    private Integer quantity;
    private byte[] contract;
    private java.sql.Date applyDate;
    private String status;
    private String materialName;
    private String description;
}

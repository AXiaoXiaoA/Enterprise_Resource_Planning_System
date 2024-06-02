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
    private Material material;

    private Long purchasingEmployeeId;
    private Employee purchasingEmployee;

    private Long companyId;
    private Company company;

    private Long repoEmployeeId;
    private Employee repoEmployee;

    private Integer quantity;
    private byte[] contract;
    private java.sql.Date applyDate;
    private String departure;
    private String status;
    private String materialName;
    private String description;
}

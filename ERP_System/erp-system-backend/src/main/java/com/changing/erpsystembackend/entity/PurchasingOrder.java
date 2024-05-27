package com.changing.erpsystembackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class PurchasingOrder {
    private Long id;
    private Long materialId;
    private Long employeeId;
    private Integer quantity;
    private byte[] contract;
    private Date applyDate;
    private Long customerId;
}

package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.SalesOrder;
import com.changing.erpsystembackend.mapper.SalesOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    // Home

    // OrderCheck

    // OrderSubmit
    public void saveSalesOrder(SalesOrder salesOrder) {
        salesOrderMapper.insertSalesOrder(salesOrder);
    }

    // OrderProcess
}

package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.SalesOrder;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesOrderMapper {
    // insert
    void insertSalesOrder(SalesOrder salesOrder);
}

package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.dto.repo.CheckProductOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.CheckSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
import com.changing.erpsystembackend.entity.SalesOrder;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SalesOrderMapper {
    // insert
    void insertSalesOrder(SalesOrder salesOrder);

    // update
    boolean checkSalesOrder(CheckSalesOrderRequestDTO checkSalesOrderRequest);
    boolean checkProductOrder(CheckProductOrderRequestDTO checkProductOrderRequest);

    // select
    List<SalesOrder> findSalesOrder(SearchSalesOrderRequestDTO searchSalesOrderRequest);
    List<SalesOrder> findSalesOrderById(Long id);
    byte[] findSalesContractById(@Param("id") Long id);
}

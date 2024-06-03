package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.dto.sales.CheckSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SalesOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SubmitSalesOrderRequestDTO;
import com.changing.erpsystembackend.entity.Company;
import com.changing.erpsystembackend.entity.Product;
import com.changing.erpsystembackend.entity.SalesOrder;
import com.changing.erpsystembackend.mapper.CompanyMapper;
import com.changing.erpsystembackend.mapper.ProductMapper;
import com.changing.erpsystembackend.mapper.SalesOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class ProductionService {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    // OrderCheck
    public List<SalesOrder> searchSalesOrder(SearchSalesOrderRequestDTO searchSalesOrderRequest) {
        return salesOrderMapper.findSalesOrder(searchSalesOrderRequest);
    }
    public List<SalesOrder> searchSalesOrderDetail(SalesOrderDetailRequestDTO salesOrderDetailRequest) {
        return salesOrderMapper.findSalesOrderById(salesOrderDetailRequest.getId());
    }
    public Object searchContract(int id) {
        return salesOrderMapper.findSalesContractById(id);
    }
    public boolean checkSalesOrder(CheckSalesOrderRequestDTO checkSalesOrderRequest) {
        return salesOrderMapper.checkSalesOrder(checkSalesOrderRequest);
    }
}

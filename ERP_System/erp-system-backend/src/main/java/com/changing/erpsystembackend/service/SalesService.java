package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.dto.sales.CheckSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SalesOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.sales.SubmitSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
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
public class SalesService {
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ProductMapper productMapper;
    // Home

    // OrderCheck
    public List<SalesOrder> searchSalesOrder(SearchSalesOrderRequestDTO searchSalesOrderRequest) {
        return salesOrderMapper.findSalesOrder(searchSalesOrderRequest);
    }
    public List<SalesOrder> searchSalesOrderDetail(SalesOrderDetailRequestDTO salesOrderDetailRequest) {
        return salesOrderMapper.findSalesOrderById(salesOrderDetailRequest.getId());
    }
    public byte[] searchContract(Long id) {
        return salesOrderMapper.findSalesContractById(id);
    }
    public boolean checkSalesOrder(CheckSalesOrderRequestDTO checkSalesOrderRequest) {
        return salesOrderMapper.checkSalesOrder(checkSalesOrderRequest);
    }

    // OrderSubmit
    public boolean submitSalesOrder(SubmitSalesOrderRequestDTO submitSalesOrderRequest) {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setSalesEmployeeId(submitSalesOrderRequest.getSalesEmployeeId());
        if (companyMapper.findCompanyIdByName(submitSalesOrderRequest.getCompanyName()) == null) {
            Company company = new Company();
            company.setName(submitSalesOrderRequest.getCompanyName());
            company.setTel(submitSalesOrderRequest.getCompanyTel());
            companyMapper.insertCompany(company);
        }
        salesOrder.setCompanyId(companyMapper.findCompanyIdByName(submitSalesOrderRequest.getCompanyName()));
        salesOrder.setQuantity(submitSalesOrderRequest.getQuantity());
        byte[] contractBytes = Base64.getDecoder().decode(submitSalesOrderRequest.getContractData().split(",")[1]);
        salesOrder.setContract(contractBytes);
        salesOrder.setApplyDate(new java.sql.Date(System.currentTimeMillis()));
        salesOrder.setDestination(submitSalesOrderRequest.getDestination());
        salesOrder.setStatus(submitSalesOrderRequest.getStatus());
        Product product = productMapper.findProductByName(submitSalesOrderRequest.getProductName());
        if (product == null) {
            product = new Product();
            product.setName(submitSalesOrderRequest.getProductName());
            product.setPrice(submitSalesOrderRequest.getPrice());
            product.setInventory(0);
            productMapper.insertProduct(product);
        }
        salesOrder.setProductId(productMapper.findProductIdByName(submitSalesOrderRequest.getProductName()));
        salesOrder.setDescription(submitSalesOrderRequest.getDescription());
        salesOrderMapper.insertSalesOrder(salesOrder);
        return true;
    }
}

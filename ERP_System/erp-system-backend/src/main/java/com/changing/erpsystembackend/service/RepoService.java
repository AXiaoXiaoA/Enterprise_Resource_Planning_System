package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.dto.purchasing.PurchasingOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SearchPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.repo.*;
import com.changing.erpsystembackend.dto.sales.SalesOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
import com.changing.erpsystembackend.entity.Material;
import com.changing.erpsystembackend.entity.Product;
import com.changing.erpsystembackend.entity.PurchasingOrder;
import com.changing.erpsystembackend.entity.SalesOrder;
import com.changing.erpsystembackend.mapper.MaterialMapper;
import com.changing.erpsystembackend.mapper.ProductMapper;
import com.changing.erpsystembackend.mapper.PurchasingOrderMapper;
import com.changing.erpsystembackend.mapper.SalesOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoService {
    @Autowired
    private PurchasingOrderMapper purchasingOrderMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ProductMapper productMapper;

    // MaterialManage
    public List<Material> searchMaterial(SearchMaterialRequestDTO searchMaterialRequest) {
        return materialMapper.findMaterial(searchMaterialRequest);
    }
    public List<PurchasingOrder> searchMaterialOrder(SearchPurchasingOrderRequestDTO searchPurchasingOrderRequest) {
        return purchasingOrderMapper.findPurchasingOrder(searchPurchasingOrderRequest);
    }
    public List<PurchasingOrder> searchMaterialOrderDetail(PurchasingOrderDetailRequestDTO purchasingOrderDetailRequest) {
        return purchasingOrderMapper.findPurchasingOrderById(purchasingOrderDetailRequest.getId());
    }
    public Object searchMaterialContract(int id) {
        return purchasingOrderMapper.findPurchasingContractById(id);
    }
    public boolean checkMaterialOrder(CheckMaterialOrderRequestDTO checkMaterialOrderRequest) {
        PurchasingOrder purchasingOrder = purchasingOrderMapper.findPurchasingOrderById(checkMaterialOrderRequest.getId()).get(0);
        Material material = materialMapper.findMaterialById(purchasingOrder.getMaterialId());
        material.setInventory(material.getInventory() + purchasingOrder.getQuantity());
        materialMapper.updateMaterial(material);
        purchasingOrderMapper.checkMaterialOrder(checkMaterialOrderRequest);
        return true;
    }
    public boolean MaterialOutbound(MaterialOutboundRequestDTO materialOutboundRequest) {
        Material material = materialMapper.findMaterialById(materialOutboundRequest.getId());
        material.setInventory(material.getInventory() - materialOutboundRequest.getQuantity());
        materialMapper.updateMaterial(material);
        return true;
    }

    // ProductManage
    public List<Product> searchProduct(SearchProductRequestDTO searchProductRequest) {
        return productMapper.findProduct(searchProductRequest);
    }
    public List<SalesOrder> searchProductOrder(SearchSalesOrderRequestDTO searchSalesOrderRequest) {
        return salesOrderMapper.findSalesOrder(searchSalesOrderRequest);
    }
    public List<SalesOrder> searchProductOrderDetail(SalesOrderDetailRequestDTO salesOrderDetailRequest) {
        return salesOrderMapper.findSalesOrderById(salesOrderDetailRequest.getId());
    }
    public Object searchProductContract(Long id) {
        return salesOrderMapper.findSalesContractById(id);
    }
    public boolean checkProductOrder(CheckProductOrderRequestDTO checkProductOrderRequest) {
        return salesOrderMapper.checkProductOrder(checkProductOrderRequest);
    }
}

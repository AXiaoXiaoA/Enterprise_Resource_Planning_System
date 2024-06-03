package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.dto.purchasing.CheckPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.PurchasingOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SearchPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SubmitPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.entity.Company;
import com.changing.erpsystembackend.entity.Material;
import com.changing.erpsystembackend.entity.PurchasingOrder;
import com.changing.erpsystembackend.mapper.CompanyMapper;
import com.changing.erpsystembackend.mapper.MaterialMapper;
import com.changing.erpsystembackend.mapper.PurchasingOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class PurchasingService {
    @Autowired
    private PurchasingOrderMapper purchasingOrderMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private MaterialMapper materialMapper;

    // Home

    // OrderCheck
    public List<PurchasingOrder> searchPurchasingOrder(SearchPurchasingOrderRequestDTO searchPurchasingOrderRequest) {
        return purchasingOrderMapper.findPurchasingOrder(searchPurchasingOrderRequest);
    }
    public List<PurchasingOrder> searchPurchasingOrderDetail(PurchasingOrderDetailRequestDTO purchasingOrderDetailRequest) {
        return purchasingOrderMapper.findPurchasingOrderById(purchasingOrderDetailRequest.getId());
    }
    public Object searchContract(int id) {
        return purchasingOrderMapper.findPurchasingContractById(id);
    }
    public boolean checkPurchasingOrder(CheckPurchasingOrderRequestDTO checkPurchasingOrderRequest) {
        return purchasingOrderMapper.checkPurchasingOrder(checkPurchasingOrderRequest);
    }

    // OrderSubmit
    public boolean submitPurchasingOrder(SubmitPurchasingOrderRequestDTO submitPurchasingOrderRequest) {
        PurchasingOrder purchasingOrder = new PurchasingOrder();
        purchasingOrder.setPurchasingEmployeeId(submitPurchasingOrderRequest.getPurchasingEmployeeId());
        if (companyMapper.findCompanyIdByName(submitPurchasingOrderRequest.getCompanyName()) == null) {
            Company company = new Company();
            company.setName(submitPurchasingOrderRequest.getCompanyName());
            company.setTel(submitPurchasingOrderRequest.getCompanyTel());
            companyMapper.insertCompany(company);
        }
        purchasingOrder.setCompanyId(companyMapper.findCompanyIdByName(submitPurchasingOrderRequest.getCompanyName()));
        purchasingOrder.setQuantity(submitPurchasingOrderRequest.getQuantity());
        if (submitPurchasingOrderRequest.getContractData() == null) {
            purchasingOrder.setContract(null);
        } else {
            purchasingOrder.setContract(Base64.getDecoder().decode(submitPurchasingOrderRequest.getContractData()));
        }
        purchasingOrder.setApplyDate(new java.sql.Date(System.currentTimeMillis()));
        purchasingOrder.setDeparture(submitPurchasingOrderRequest.getDeparture());
        purchasingOrder.setStatus(submitPurchasingOrderRequest.getStatus());
        Material material = materialMapper.findMaterialByName(submitPurchasingOrderRequest.getMaterialName());
        if (material == null) {
            material = new Material();
            material.setName(submitPurchasingOrderRequest.getMaterialName());
            material.setPrice(submitPurchasingOrderRequest.getPrice());
            material.setInventory(0);
            materialMapper.insertMaterial(material);
        }
        purchasingOrder.setMaterialId(materialMapper.findMaterialIdByName(submitPurchasingOrderRequest.getMaterialName()));
        purchasingOrder.setDescription(submitPurchasingOrderRequest.getDescription());
        purchasingOrderMapper.insertPurchasingOrder(purchasingOrder);
        System.out.println("1");
        return true;
    }
}

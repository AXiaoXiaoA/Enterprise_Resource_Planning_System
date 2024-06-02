package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.dto.purchasing.CheckPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SearchPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.entity.PurchasingOrder;
import com.changing.erpsystembackend.entity.SalesOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchasingOrderMapper {
    // insert
    void insertPurchasingOrder(PurchasingOrder purchasingOrder);

    // update
    boolean checkPurchasingOrder(CheckPurchasingOrderRequestDTO checkPurchasingOrderRequest);

    // select
    List<PurchasingOrder> findPurchasingOrder(SearchPurchasingOrderRequestDTO searchPurchasingOrderRequest);
    List<PurchasingOrder> findPurchasingOrderById(Long id);
    List<PurchasingOrder> findPurchasingContractById(@Param("id") int id);
}

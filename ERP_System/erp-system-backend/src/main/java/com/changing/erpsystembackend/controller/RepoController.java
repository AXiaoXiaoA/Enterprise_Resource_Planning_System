package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.dto.purchasing.PurchasingOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SearchPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.repo.*;
import com.changing.erpsystembackend.dto.sales.SalesOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
import com.changing.erpsystembackend.service.RepoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repo")
public class RepoController {
	@Autowired
	private RepoService repoService;

	// MaterialManage
	@PostMapping("/searchMaterial")
	public Result searchMaterial(@RequestBody SearchMaterialRequestDTO searchMaterialRequest) {
		return Result.success(repoService.searchMaterial(searchMaterialRequest));
	}
	@PostMapping("/searchMaterialOrder")
	public Result searchMaterialOrder(@RequestBody SearchPurchasingOrderRequestDTO searchPurchasingOrderRequest) {
		return Result.success(repoService.searchMaterialOrder(searchPurchasingOrderRequest));
	}
	@PostMapping("/searchMaterialOrderDetail")
	public Result searchMaterialOrderDetail(@RequestBody PurchasingOrderDetailRequestDTO purchasingOrderDetailRequest) {
		return Result.success(repoService.searchMaterialOrderDetail(purchasingOrderDetailRequest));
	}
	@GetMapping("/searchMaterialContract")
	public Result searchMaterialContract(@RequestParam("id") int id) {
		return Result.success(repoService.searchMaterialContract(id));
	}
	@PostMapping("/checkPurchasingOrder")
	public Result checkPurchasingOrder(@RequestBody CheckMaterialOrderRequestDTO checkMaterialOrderRequest){
		return Result.success(repoService.checkMaterialOrder(checkMaterialOrderRequest));
	}
	@PostMapping("/materialOutbound")
	public Result MaterialOutbound(@RequestBody MaterialOutboundRequestDTO materialOutboundRequest) {
		return Result.success(repoService.MaterialOutbound(materialOutboundRequest));
	}

	// ProductManage
	@PostMapping("/searchProduct")
	public Result searchProduct(@RequestBody SearchProductRequestDTO searchProductRequest) {
		return Result.success(repoService.searchProduct(searchProductRequest));
	}
	@PostMapping("/searchProductOrder")
	public Result searchProductOrder(@RequestBody SearchSalesOrderRequestDTO searchSalesOrderRequest) {
		return Result.success(repoService.searchProductOrder(searchSalesOrderRequest));
	}
	@PostMapping("/searchProductOrderDetail")
	public Result searchProductOrderDetail(@RequestBody SalesOrderDetailRequestDTO salesOrderDetailRequest) {
		return Result.success(repoService.searchProductOrderDetail(salesOrderDetailRequest));
	}
	@GetMapping("/searchProductContract")
	public Result searchProductContract(@RequestParam("id") int id) {
		return Result.success(repoService.searchProductContract(id));
	}
	@PostMapping("/checkProductOrder")
	public Result checkProductOrder(@RequestBody CheckProductOrderRequestDTO checkProductOrderRequest){
		return Result.success(repoService.checkProductOrder(checkProductOrderRequest));
	}
}

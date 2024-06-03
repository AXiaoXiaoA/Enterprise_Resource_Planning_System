package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.dto.sales.CheckSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SalesOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SubmitSalesOrderRequestDTO;
import com.changing.erpsystembackend.service.ProductionService;
import com.changing.erpsystembackend.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/production")
public class ProductionController {
	@Autowired
	private ProductionService productionService;

	// OrderCheck
	@PostMapping("/searchOrder")
	public Result searchOrder(@RequestBody SearchSalesOrderRequestDTO searchSalesOrderRequest) {
		return Result.success(productionService.searchSalesOrder(searchSalesOrderRequest));
	}
	@PostMapping("/searchOrderDetail")
	public Result searchOrderDetail(@RequestBody SalesOrderDetailRequestDTO salesOrderDetailRequest) {
		return Result.success(productionService.searchSalesOrderDetail(salesOrderDetailRequest));
	}
	@GetMapping("/searchContract")
	public Result searchContract(@RequestParam("id") int id) {
		return Result.success(productionService.searchContract(id));
	}
	@PostMapping("/checkOrder")
	public Result checkOrder(@RequestBody CheckSalesOrderRequestDTO checkSalesOrderRequest){
		return Result.success(productionService.checkSalesOrder(checkSalesOrderRequest));
	}
}

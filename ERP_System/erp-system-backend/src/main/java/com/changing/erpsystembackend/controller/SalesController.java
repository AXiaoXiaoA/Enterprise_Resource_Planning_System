package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.dto.sales.CheckSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SalesOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.sales.SubmitSalesOrderRequestDTO;
import com.changing.erpsystembackend.dto.sales.SearchSalesOrderRequestDTO;
import com.changing.erpsystembackend.service.SalesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SalesController {
	@Autowired
	private SalesService salesService;

	// Home


	// OrderCheck
	@PostMapping("/searchOrder")
	public Result searchOrder(@RequestBody SearchSalesOrderRequestDTO searchSalesOrderRequest) {
		return Result.success(salesService.searchSalesOrder(searchSalesOrderRequest));
	}
	@PostMapping("/searchOrderDetail")
	public Result searchOrderDetail(@RequestBody SalesOrderDetailRequestDTO salesOrderDetailRequest) {
		return Result.success(salesService.searchSalesOrderDetail(salesOrderDetailRequest));
	}
	@GetMapping("/searchContract")
	public Result searchContract(@RequestParam("id") Long id) {
		byte[] contract = salesService.searchContract(id);
		return Result.success(contract);
	}
	@PostMapping("/checkOrder")
	public Result checkOrder(@RequestBody CheckSalesOrderRequestDTO checkSalesOrderRequest){
		return Result.success(salesService.checkSalesOrder(checkSalesOrderRequest));
	}

	// OrderSubmit
	@PostMapping("/submitOrder")
	public Result submitOrder(@RequestBody SubmitSalesOrderRequestDTO submitSalesOrderRequest) {
		return Result.success(salesService.submitSalesOrder(submitSalesOrderRequest));
	}
}

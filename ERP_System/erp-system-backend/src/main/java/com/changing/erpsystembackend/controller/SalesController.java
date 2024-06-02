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
	SalesService salesService;

	// Home


	// OrderCheck
	@PostMapping("/searchSalesOrder")
	public Result searchSalesOrder(@RequestBody SearchSalesOrderRequestDTO searchSalesOrderRequest) {
		return Result.success(salesService.searchSalesOrder(searchSalesOrderRequest));
	}
	@PostMapping("/searchSalesOrderDetail")
	public Result searchSalesOrderDetail(@RequestBody SalesOrderDetailRequestDTO salesOrderDetailRequest) {
		return Result.success(salesService.searchSalesOrderDetail(salesOrderDetailRequest));
	}
	@GetMapping("/searchContract")
	public Result searchContract(@RequestParam("id") int id) {
		return Result.success(salesService.searchContract(id));
	}
	@PostMapping("/acceptSalesOrder")
	public Result acceptSalesOrder(@RequestBody CheckSalesOrderRequestDTO checkSalesOrderRequest){
		if(salesService.checkSalesOrder(checkSalesOrderRequest)){
			return Result.success("通过成功");
		}else{
			return Result.error("通过失败");
		}
	}
	@PostMapping("/rejectSalesOrder")
	public Result rejectSalesOrder(@RequestBody CheckSalesOrderRequestDTO checkSalesOrderRequest){
		if(salesService.checkSalesOrder(checkSalesOrderRequest)){
			return Result.success("打回成功");
		}else{
			return Result.error("打回失败");
		}
	}
	@PostMapping("/submitSalesOrderAgain")
	public Result submitSalesOrderAgain(@RequestBody CheckSalesOrderRequestDTO checkSalesOrderRequest){
		if(salesService.checkSalesOrder(checkSalesOrderRequest)){
			return Result.success("提交成功");
		}else{
			return Result.error("提交失败");
		}
	}

	// OrderSubmit
	@PostMapping("/submitOrder")
	public Result submitSalesOrder(@RequestBody SubmitSalesOrderRequestDTO submitSalesOrderRequest) {
		try {
			if (salesService.submitSalesOrder(submitSalesOrderRequest)) {
				return Result.success("订单提交成功");
			} else {
				return Result.error("订单提交失败");
			}
		} catch (Exception e) {
			return Result.error("订单提交失败: " + e.getMessage());
		}
	}
}

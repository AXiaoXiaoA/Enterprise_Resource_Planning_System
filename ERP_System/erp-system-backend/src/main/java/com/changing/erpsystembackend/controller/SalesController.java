package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.entity.SalesOrder;
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


	// OrderSubmit
	@PostMapping("/submitOrder")
	public Result submitSalesOrder(@RequestBody SalesOrder salesOrder) {
		try {
			salesService.saveSalesOrder(salesOrder);
			return Result.success("订单提交成功");
		} catch (Exception e) {
			return Result.error("订单提交失败: " + e.getMessage());
		}
	}

	// OrderProcess

}

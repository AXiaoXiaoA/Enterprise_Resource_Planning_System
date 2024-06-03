package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.dto.purchasing.CheckPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.PurchasingOrderDetailRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SearchPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.dto.purchasing.SubmitPurchasingOrderRequestDTO;
import com.changing.erpsystembackend.service.PurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchasing")
public class PurchasingController {
	@Autowired
	private PurchasingService purchasingService;

	// Home


	// OrderCheck
	@PostMapping("/searchOrder")
	public Result searchOrder(@RequestBody SearchPurchasingOrderRequestDTO searchPurchasingOrderRequest) {
		return Result.success(purchasingService.searchPurchasingOrder(searchPurchasingOrderRequest));
	}
	@PostMapping("/searchOrderDetail")
	public Result searchOrderDetail(@RequestBody PurchasingOrderDetailRequestDTO purchasingOrderDetailRequest) {
		return Result.success(purchasingService.searchPurchasingOrderDetail(purchasingOrderDetailRequest));
	}
	@GetMapping("/searchContract")
	public Result searchContract(@RequestParam("id") int id) {
		return Result.success(purchasingService.searchContract(id));
	}
	@PostMapping("/checkOrder")
	public Result checkOrder(@RequestBody CheckPurchasingOrderRequestDTO checkPurchasingOrderRequest){
		return Result.success(purchasingService.checkPurchasingOrder(checkPurchasingOrderRequest));
	}

	// OrderSubmit
	@PostMapping("/submitOrder")
	public Result submitOrder(@RequestBody SubmitPurchasingOrderRequestDTO submitPurchasingOrderRequest) {
		try {
			if (purchasingService.submitPurchasingOrder(submitPurchasingOrderRequest)) {
				return Result.success("订单提交成功");
			} else {
				return Result.error("订单提交失败");
			}
		} catch (Exception e) {
			return Result.error("订单提交失败: " + e.getMessage());
		}
	}
}

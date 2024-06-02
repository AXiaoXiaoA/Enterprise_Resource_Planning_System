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
	PurchasingService purchasingService;

	// Home


	// OrderCheck
	@PostMapping("/searchPurchasingOrder")
	public Result searchPurchasingOrder(@RequestBody SearchPurchasingOrderRequestDTO searchPurchasingOrderRequest) {
		return Result.success(purchasingService.searchPurchasingOrder(searchPurchasingOrderRequest));
	}
	@PostMapping("/searchPurchasingOrderDetail")
	public Result searchPurchasingOrderDetail(@RequestBody PurchasingOrderDetailRequestDTO purchasingOrderDetailRequest) {
		return Result.success(purchasingService.searchPurchasingOrderDetail(purchasingOrderDetailRequest));
	}
	@GetMapping("/searchContract")
	public Result searchContract(@RequestParam("id") int id) {
		return Result.success(purchasingService.searchContract(id));
	}
	@PostMapping("/acceptPurchasingOrder")
	public Result acceptPurchasingOrder(@RequestBody CheckPurchasingOrderRequestDTO checkPurchasingOrderRequest){
		if(purchasingService.checkPurchasingOrder(checkPurchasingOrderRequest)){
			return Result.success("通过成功");
		}else{
			return Result.error("通过失败");
		}
	}
	@PostMapping("/rejectPurchasingOrder")
	public Result rejectPurchasingOrder(@RequestBody CheckPurchasingOrderRequestDTO checkPurchasingOrderRequest){
		if(purchasingService.checkPurchasingOrder(checkPurchasingOrderRequest)){
			return Result.success("打回成功");
		}else{
			return Result.error("打回失败");
		}
	}
	@PostMapping("/submitPurchasingOrderAgain")
	public Result submitPurchasingOrderAgain(@RequestBody CheckPurchasingOrderRequestDTO checkPurchasingOrderRequest){
		if(purchasingService.checkPurchasingOrder(checkPurchasingOrderRequest)){
			return Result.success("提交成功");
		}else{
			return Result.error("提交失败");
		}
	}

	// OrderSubmit
	@PostMapping("/submitOrder")
	public Result submitPurchasingOrder(@RequestBody SubmitPurchasingOrderRequestDTO submitPurchasingOrderRequest) {
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

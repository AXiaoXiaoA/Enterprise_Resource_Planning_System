package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.common.StringRequest;
import com.changing.erpsystembackend.service.PersonnelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personnel")
public class PersonnelController {
	@Resource
	PersonnelService personnelService;

	// Home
	@GetMapping("/employeeDistribution")
	public int getEmployeeDistribution() {
		return personnelService.getEmployeeDistribution();
	}
	@GetMapping("/ageDistribution")
	public List<Map<String, Object>> getAgeDistribution() {
		return personnelService.getAgeDistribution();
	}
	@GetMapping("/departmentDistribution")
	public List<Map<String, Object>> getDepartmentDistribution() {
		return personnelService.getDepartmentDistribution();
	}
	@GetMapping("/todayApplyDistribution")
	public int getApplyDistribution() {
		return personnelService.getApplyDistribution();
	}

	// ApplyReview
	@GetMapping("/searchAllApply")
	public Result showAllApply() {
		return Result.success(personnelService.searchAllApply());
	}
	@GetMapping("/searchApply")
	public Result searchApply(@RequestParam Map<String, String> searchCriteria) {
		return Result.success(personnelService.searchApply(searchCriteria));
	}
	@GetMapping("/searchResume")
	public Result searchResume(@RequestParam("id") int id) {
		return Result.success(personnelService.searchResume(id));
	}
	@PostMapping("/analysis")
	public Result outWroughtIron(@RequestBody StringRequest requestText){
		String text = requestText.getContent();
		if( text == null || text.equals("")){
			return Result.error("无效输入，无法进行分析！");
		}else{
			String result = personnelService.executePythonScript(text);
			System.out.println("result = " + result);
			return Result.success(result);
		}
	}
	@PostMapping("/acceptApply")
	public Result accept(@RequestParam("id") int id){
		if(personnelService.acceptApply(id)){
			return Result.success();
		}else{
			return Result.error("雇佣失败");
		}
	}
	@PostMapping("/rejectApply")
	public Result reject(@RequestParam("id") int id){
		if(personnelService.rejectApply(id)){
			return Result.success();
		}else{
			return Result.error("雇佣失败");
		}
	}

	// EmployeeManagement
	@GetMapping("/searchAllEmployee")
	public Result showAllEmployee() {
		return Result.success(personnelService.searchAllEmployee());
	}
	@GetMapping("/searchEmployee")
	public Result searchEmployee(@RequestParam Map<String, String> searchCriteria) {
		return Result.success(personnelService.searchEmployee(searchCriteria));
	}
}

package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.dto.personnel.ResumeAnalysisRequest;
import com.changing.erpsystembackend.service.PersonnelService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/personnel")
public class PersonnelController {
	@Resource
	PersonnelService personnelService;

	// Home
	@GetMapping("/employeeDistribution")
	public Result getEmployeeDistribution() {
		return Result.success(personnelService.getEmployeeDistribution());
	}
	@GetMapping("/ageDistribution")
	public Result getAgeDistribution() {
		return Result.success(personnelService.getAgeDistribution());
	}
	@GetMapping("/departmentDistribution")
	public Result getDepartmentDistribution() {
		return Result.success(personnelService.getDepartmentDistribution());
	}
	@GetMapping("/todayApplyDistribution")
	public Result getApplyDistribution() {
		return Result.success(personnelService.getApplyDistribution());
	}

	// ApplyReview
	@GetMapping("/searchAllApply")
	public Result showAllApply(@RequestParam Map<String, String> searchCriteria) {
		return Result.success(personnelService.searchAllApply(searchCriteria));
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
	public Result outWroughtIron(@RequestBody ResumeAnalysisRequest requestText){
		String text = requestText.getContent();
		if( text == null || text.isEmpty()){
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
			return Result.success("聘用成功");
		}else{
			return Result.error("聘用失败");
		}
	}
	@PostMapping("/rejectApply")
	public Result reject(@RequestParam("id") int id){
		if(personnelService.rejectApply(id)){
			return Result.success("拒绝成功");
		}else{
			return Result.error("拒绝失败");
		}
	}

	// EmployeeManagement
	@GetMapping("/searchAllEmployee")
	public Result showAllEmployee(@RequestParam Map<String, String> searchCriteria) {
		return Result.success(personnelService.searchAllEmployee(searchCriteria));
	}
	@GetMapping("/searchEmployee")
	public Result searchEmployee(@RequestParam Map<String, String> searchCriteria) {
		return Result.success(personnelService.searchEmployee(searchCriteria));
	}
}

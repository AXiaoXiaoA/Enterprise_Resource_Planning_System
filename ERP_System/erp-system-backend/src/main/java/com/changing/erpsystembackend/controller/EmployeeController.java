package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.dto.employee.LoginRequestDTO;
import com.changing.erpsystembackend.dto.employee.RegisterRequestDTO;
import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequestDTO loginRequest) {
        Employee employee = employeeService.login(loginRequest);
        if(employee == null){
            return Result.error("输入的用户名或者密码错误");
        }else{
            return Result.success(employee);
        }
    }

    @PostMapping("/register")
    public Result register(@ModelAttribute RegisterRequestDTO registerRequest, @RequestPart("resume") MultipartFile resume) {
        registerRequest.setResume(resume);
        if (employeeService.register(registerRequest)) {
            return Result.success("申请提交成功");
        }
        return Result.error("申请提交失败");
    }
}

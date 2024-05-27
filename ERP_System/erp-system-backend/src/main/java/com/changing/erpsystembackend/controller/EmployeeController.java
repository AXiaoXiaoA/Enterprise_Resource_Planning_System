package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.entity.PersonalInformation;
import com.changing.erpsystembackend.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginRequest) {
        Long tel = Long.valueOf(loginRequest.get("tel"));
        String password = loginRequest.get("password");
        Employee employee = employeeService.login(tel, password);
        if(employee == null){
            return Result.error("输入的用户名或者密码错误");
        }else{
            return Result.success(employee);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestParam("id") String id,
                           @RequestParam("name") String name,
                           @RequestParam("gender") String gender,
                           @RequestParam("nationality") String nationality,
                           @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date birthday,
                           @RequestParam("birthplace") String birthplace,
                           @RequestParam("politicalStatus") String politicalStatus,
                           @RequestParam("tel") Long tel,
                           @RequestParam("email") String email,
                           @RequestParam("department") String department,
                           @RequestParam("position") String position,
                           @RequestParam("resume") MultipartFile resume) {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setId(id);
        personalInformation.setName(name);
        personalInformation.setGender(gender);
        personalInformation.setNationality(nationality);
        personalInformation.setBirthday(new java.sql.Date(birthday.getTime()));
        personalInformation.setBirthplace(birthplace);
        personalInformation.setPoliticalStatus(politicalStatus);
        personalInformation.setTel(tel);
        personalInformation.setEmail(email);

        Employee employee = new Employee();
        employee.setPersonId(personalInformation.getId());
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setStartDate(new java.sql.Date(System.currentTimeMillis()));
        employee.setStatus("apply");
        if (!resume.isEmpty()) {
            try {
                String content = new String(resume.getBytes(), StandardCharsets.UTF_8);
                employee.setResume(content);
            } catch (IOException e) {
                return Result.error("文件读取失败");
            }
        }
        if (employeeService.register(employee, personalInformation) == true) {
            return Result.success("申请提交成功");
        }
        return Result.error("申请提交失败");
    }
}

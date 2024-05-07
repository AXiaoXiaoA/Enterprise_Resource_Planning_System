package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.service.EmployeeServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeControllerTest {

    private final EmployeeServiceTest employeeService;

    @Autowired
    public EmployeeControllerTest(EmployeeServiceTest employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/create")
    public void createEmployee() {
        employeeService.createEmployee();
    }

    @GetMapping("/read")
    public Employee getEmployeeById() {
        return employeeService.getEmployeeById();
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/update")
    public void updateEmployee() {
        employeeService.updateEmployee();
    }

    @GetMapping("/delete")
    public void deleteEmployee() {
        employeeService.deleteEmployee();
    }
}

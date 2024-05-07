package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceTest {

    private final EmployeeMapper mapper;

    @Autowired
    public EmployeeServiceTest(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    public void createEmployee() {
        Employee employee1 = new Employee();
        employee1.setEmployeeId(123);
        employee1.setPassword("pass123");
        employee1.setDepartment("IT");
        employee1.setPosition("Engineer");
        employee1.setStartDate(new Date());
        employee1.setName("John Doe");
        employee1.setTel("1234567890");
        employee1.setEmail("johndoe@example.com");
        employee1.setGender("Male");
        employee1.setBirthday(new Date());
        employee1.setBirthplace("New York");
        employee1.setNationality("American");
        employee1.setIdCardNumber("123456789012345678");
        employee1.setPoliticalStatus("Citizen");
        employee1.setCv("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        mapper.createEmployee(employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(456);
        employee2.setPassword("pass123");
        employee2.setDepartment("HR");
        employee2.setPosition("SB");
        employee2.setStartDate(new Date());
        employee2.setName("Changee Shaw");
        employee2.setTel("1234567890");
        employee2.setEmail("johndoe@example.com");
        employee2.setGender("Male");
        employee2.setBirthday(new Date());
        employee2.setBirthplace("New York");
        employee2.setNationality("American");
        employee2.setIdCardNumber("123456789012345678");
        employee2.setPoliticalStatus("Citizen");
        employee2.setCv("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        mapper.createEmployee(employee2);
    }

    public Employee getEmployeeById() {
        long employeeId = 123;
        return mapper.findEmployeeById(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return mapper.findAll();
    }

    public void updateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(123);
        employee.setPassword("pass123");
        employee.setDepartment("IT");
        employee.setPosition("Engineer");
        employee.setStartDate(new Date());
        employee.setName("Xiao Chengjun");
        employee.setTel("1234567890");
        employee.setEmail("johndoe@example.com");
        employee.setGender("Male");
        employee.setBirthday(new Date());
        employee.setBirthplace("New York");
        employee.setNationality("American");
        employee.setIdCardNumber("123456789012345678");
        employee.setPoliticalStatus("Citizen");
        employee.setCv("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        mapper.updateEmployee(employee);
    }

    public void deleteEmployee() {
        long employeeId = 456;
        mapper.deleteEmployeeById(employeeId);
    }
}

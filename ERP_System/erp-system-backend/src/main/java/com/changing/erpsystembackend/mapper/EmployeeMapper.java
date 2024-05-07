package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    void createEmployee(Employee employee);

    Employee findEmployeeById(long employeeId);
    Employee findEmployeeByName(String name);
    Employee findEmployeeByDepartment(String department);
    Employee findEmployeeByPosition(String position);
    Employee findEmployeeByTel(String tel);
    Employee findEmployeeByGender(String gender);
    Employee findEmployeeByPoliticalStatus(String politicalStatus);
    Employee findEmployeeByIdAndPassword(long employeeId, String password);
    java.util.List<Employee> findAll();

    void deleteEmployeeById(long employeeId);

    void updateEmployee(Employee employee);
}
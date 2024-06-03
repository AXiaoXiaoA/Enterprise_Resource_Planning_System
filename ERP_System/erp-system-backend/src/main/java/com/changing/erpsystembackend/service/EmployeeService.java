package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.dto.employee.LoginRequestDTO;
import com.changing.erpsystembackend.dto.employee.RegisterRequestDTO;
import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.entity.Person;
import com.changing.erpsystembackend.mapper.EmployeeMapper;
import com.changing.erpsystembackend.mapper.PersonMapper;
import com.changing.erpsystembackend.util.DateConversionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PersonMapper personMapper;

    public Employee login(LoginRequestDTO loginRequest) {
        Long tel = loginRequest.getTel();
        String password = loginRequest.getPassword();
        return employeeMapper.findEmployeeByTelAndPassword(tel, password);
    }
    public boolean register(RegisterRequestDTO registerRequest) {
        Person person = new Person();
        person.setId(registerRequest.getId());
        person.setName(registerRequest.getName());
        person.setGender(registerRequest.getGender());
        person.setNationality(registerRequest.getNationality());
        person.setBirthday(DateConversionUtils.toSqlDate(java.sql.Date.valueOf(registerRequest.getBirthday())));
        person.setBirthplace(registerRequest.getBirthplace());
        person.setPoliticalStatus(registerRequest.getPoliticalStatus());
        person.setTel(registerRequest.getTel());
        person.setEmail(registerRequest.getEmail());

        Employee employee = new Employee();
        employee.setPersonId(registerRequest.getId());
        employee.setDepartment(registerRequest.getDepartment());
        employee.setPosition(registerRequest.getPosition());
        employee.setStartDate(new java.sql.Date(System.currentTimeMillis()));
        employee.setStatus("应聘中");
        if (registerRequest.getResume() != null) {
            try {
                String content = new String(registerRequest.getResume().getBytes(), StandardCharsets.UTF_8);
                employee.setResume(content);
            } catch (IOException e) {
                return false;
            }
        }
        try {
            if (personMapper.findEmployeeByID(person.getId()) != null) {
                return false;
            }
            personMapper.insertPerson(person);
            employeeMapper.insertEmployee(employee);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getPersonalInfo(Long id) {
        return employeeMapper.findEmployeeByPersonId(id);
    }
}

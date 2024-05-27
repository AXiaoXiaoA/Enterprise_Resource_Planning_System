package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.entity.PersonalInformation;
import com.changing.erpsystembackend.mapper.EmployeeMapper;

import com.changing.erpsystembackend.mapper.PersonalInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PersonalInformationMapper personalInformationMapper;

    public Employee login(Long tel, String password) {
        return employeeMapper.findByTelAndPassword(tel, password);
    }
    public boolean register(Employee employee, PersonalInformation personalInformation) {
        try {
            if (personalInformationMapper.findByID(personalInformation.getId()) != null) {
                return false;
            }
            personalInformationMapper.insertPersonalInformation(personalInformation);
            employeeMapper.insertEmployee(employee);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

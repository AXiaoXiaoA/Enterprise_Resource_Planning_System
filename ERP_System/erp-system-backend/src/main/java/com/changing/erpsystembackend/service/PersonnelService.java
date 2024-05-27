package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.mapper.EmployeeMapper;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class PersonnelService {
    @Resource
    EmployeeMapper employeeMapper;

    // Home
    public int getEmployeeDistribution() {
        return employeeMapper.getEmployeeDistribution();
    }
    public List<Map<String, Object>> getAgeDistribution() {
        return employeeMapper.getAgeDistribution();
    }
    public List<Map<String, Object>> getDepartmentDistribution() {
        return employeeMapper.getDepartmentDistribution();
    }
    public int getApplyDistribution() {
        return employeeMapper.getApplyDistribution();
    }

    // ApplyCheck
    public List<Employee> searchAllApply(){
        return employeeMapper.searchAllApply();
    }
    public List<Employee> searchApply(Map<String, String> searchCriteria) {
        return employeeMapper.searchApply(searchCriteria);
    }
    public String searchResume(int selectedId){
        return employeeMapper.searchResumeById(selectedId);
    }
    public String executePythonScript(String text) {
        String scriptPath = "D:\\陆\\大创\\test.py";
        try {
            ProcessBuilder builder = new ProcessBuilder("python", scriptPath, text);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Handle the error scenario here
                return "Error in running script";
            }

            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during script execution: " + e.getMessage();
        }
    }
    public boolean acceptApply(int selectedId) {
        return employeeMapper.acceptApply(selectedId);
    }
    public boolean rejectApply(int selectedId) {
        return employeeMapper.rejectApply(selectedId);
    }

    // EmployeeManage
    public List<Employee> searchAllEmployee() {
        return employeeMapper.searchAllEmployee();
    }
    public List<Employee> searchEmployee(Map<String, String> searchCriteria) {
        return employeeMapper.searchEmployee(searchCriteria);
    }
}

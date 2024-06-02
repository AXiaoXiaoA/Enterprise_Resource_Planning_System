package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.dto.personnel.SearchEmployeeRequestDTO;
import com.changing.erpsystembackend.entity.Employee;
import com.changing.erpsystembackend.mapper.EmployeeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class PersonnelService {
    @Autowired
    private EmployeeMapper employeeMapper;

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
    public String searchResume(int id){
        return employeeMapper.findResumeById(id);
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
    public boolean acceptApply(int id) {
        return employeeMapper.acceptApply(id);
    }
    public boolean rejectApply(int id) {
        return employeeMapper.rejectApply(id);
    }

    // EmployeeManage
    public List<Employee> searchEmployee(SearchEmployeeRequestDTO searchEmployeeRequest) {
        return employeeMapper.findEmployee(searchEmployeeRequest);
    }
}

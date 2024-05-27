package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.User;
import com.changing.erpsystembackend.mapper.UserMapper;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class PersonnelService {
    @Resource
    UserMapper userMapper;

    // Home
    public int getEmployeeDistribution() {
        return userMapper.getEmployeeDistribution();
    }
    public List<Map<String, Object>> getAgeDistribution() {
        return userMapper.getAgeDistribution();
    }
    public List<Map<String, Object>> getDepartmentDistribution() {
        return userMapper.getDepartmentDistribution();
    }
    public int getApplyDistribution() {
        return userMapper.getApplyDistribution();
    }

    // ApplyCheck
    public List<User> searchAllApply(){
        return userMapper.searchAllApply();
    }
    public List<User> searchApply(Map<String, String> searchCriteria) {
        return userMapper.searchApply(searchCriteria);
    }
    public String searchResume(int selectedId){
        return userMapper.searchResumeById(selectedId);
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
        return userMapper.acceptApply(selectedId);
    }
    public boolean rejectApply(int selectedId) {
        return userMapper.rejectApply(selectedId);
    }

    // EmployeeManage
    public List<User> searchAllEmployee() {
        return userMapper.searchAllEmployee();
    }
    public List<User> searchEmployee(Map<String, String> searchCriteria) {
        return userMapper.searchEmployee(searchCriteria);
    }
}

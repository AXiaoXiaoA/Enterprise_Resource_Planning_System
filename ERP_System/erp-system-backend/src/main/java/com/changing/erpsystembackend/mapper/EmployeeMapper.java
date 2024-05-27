package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.Employee;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.ClobTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
    // login and register
    Employee findByTelAndPassword(@Param("tel") Long tel, @Param("password") String password);
    void insertEmployee(Employee employee);

    // personnel-Home
    int getEmployeeDistribution();
    List<Map<String, Object>> getAgeDistribution();
    List<Map<String, Object>> getDepartmentDistribution();
    int getApplyDistribution();

    // personnel-ApplyCheck
    List<Employee> searchAllApply();
    List<Employee> searchApply(@Param("searchCriteria") Map<String, String> searchCriteria);
    @Results({@Result(column = "resume", property = "resume", jdbcType = JdbcType.CLOB, typeHandler = ClobTypeHandler.class)})
    String searchResumeById(int id);
    boolean acceptApply(int id);
    boolean rejectApply(int id);

    // personnel-EmployeeManage
    List<Employee> searchAllEmployee();
    List<Employee> searchEmployee(@Param("searchCriteria") Map<String, String> searchCriteria);
}

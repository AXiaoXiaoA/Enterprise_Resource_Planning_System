package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.dto.personnel.SearchEmployeeRequestDTO;
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
    // insert
    void insertEmployee(Employee employee);

    // update
    boolean acceptApply(@Param("id") int id);
    boolean rejectApply(@Param("id") int id);

    // select
    Employee findEmployeeByTelAndPassword(@Param("tel") Long tel, @Param("password") String password);
    int getEmployeeDistribution();
    List<Map<String, Object>> getAgeDistribution();
    List<Map<String, Object>> getDepartmentDistribution();
    int getApplyDistribution();
    List<Employee> findEmployee(SearchEmployeeRequestDTO searchEmployeeRequest);
    @Results({@Result(column = "resume", property = "resume", jdbcType = JdbcType.CLOB, typeHandler = ClobTypeHandler.class)})
    String findResumeById(int id);
}

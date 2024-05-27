package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.ClobTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    // login and register
    User selectEmployee(User user);
    void insertEmployee(User user);

    // personnel-Home
    int getEmployeeDistribution();
    List<Map<String, Object>> getAgeDistribution();
    List<Map<String, Object>> getDepartmentDistribution();
    int getApplyDistribution();

    // personnel-ApplyCheck
    List<User> searchAllApply();
    List<User> searchApply(@Param("searchCriteria") Map<String, String> searchCriteria);
    @Results({@Result(column = "resume", property = "resume", jdbcType = JdbcType.CLOB, typeHandler = ClobTypeHandler.class)})
    String searchResumeById(int id);
    boolean acceptApply(int id);
    boolean rejectApply(int id);

    // personnel-EmployeeManage
    List<User> searchAllEmployee();
    List<User> searchEmployee(@Param("searchCriteria") Map<String, String> searchCriteria);
}

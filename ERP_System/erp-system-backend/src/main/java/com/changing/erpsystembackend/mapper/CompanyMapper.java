package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMapper {
    // insert
    void insertCompany(Company company);

    // select
    Long findCompanyIdByName(@Param("name") String name);
}

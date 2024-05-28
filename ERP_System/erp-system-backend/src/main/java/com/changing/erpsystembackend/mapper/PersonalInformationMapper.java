package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.PersonalInformation;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInformationMapper {
    // insert
    void insertPersonalInformation(PersonalInformation personalInformation);
    // select
    PersonalInformation findEmployeeByID(String id);
}

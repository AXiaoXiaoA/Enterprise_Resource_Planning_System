package com.changing.erpsystembackend.mapper;


import com.changing.erpsystembackend.entity.PersonalInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInformationMapper {
    // register
    PersonalInformation findByID(String id);
    void insertPersonalInformation(PersonalInformation personalInformation);
}

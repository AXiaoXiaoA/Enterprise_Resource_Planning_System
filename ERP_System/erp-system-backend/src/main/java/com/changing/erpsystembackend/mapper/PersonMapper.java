package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.entity.Person;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper {
    // insert
    void insertPerson(Person person);
    // select
    Person findEmployeeByID(String id);
}

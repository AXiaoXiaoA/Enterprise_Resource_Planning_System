package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String id;
    private String name;
    private String gender;
    private String nationality;
    private java.sql.Date birthday;
    private String birthplace;
    private String politicalStatus;
    private Long tel;
    private String email;
}

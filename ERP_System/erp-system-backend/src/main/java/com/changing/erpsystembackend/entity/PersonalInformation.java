package com.changing.erpsystembackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class PersonalInformation {
    private String id;
    private String name;
    private String gender;
    private String nationality;
    private Date birthday;
    private String birthplace;
    private String politicalStatus;
    private Long tel;
    private String email;
}

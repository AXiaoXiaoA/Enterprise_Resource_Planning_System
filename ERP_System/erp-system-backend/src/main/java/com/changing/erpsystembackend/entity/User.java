package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long Id;
    private String tel;
    private String email;
    private String password;
    private String department;
    private String position;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private String status;

    private String name;
    private String gender;
    private String age;
    private java.util.Date birthday;
    private String birthplace;
    private String nationality;
    private String idCardNumber;
    private String politicalStatus;
    private String resume;
}

package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private long employeeId;
    private String password;
    private String department;
    private String position;
    private java.util.Date startDate;

    private String name;
    private String tel;
    private String email;
    private String gender;
    private java.util.Date birthday;
    private String birthplace;
    private String nationality;
    private String idCardNumber;
    private String politicalStatus;
    private String cv;
}

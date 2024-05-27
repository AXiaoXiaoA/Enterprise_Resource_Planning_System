package com.changing.erpsystembackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class Employee {
    private Long id;
    private String password;
    private String department;
    private String position;
    private Date startDate;
    private Date endDate;
    private String status;
    private String resume;
    private String personId;
}

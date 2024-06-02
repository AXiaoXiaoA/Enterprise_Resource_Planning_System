package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String personId;
    private String department;
    private String position;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private String status;
    private String password;
    private String resume;

    private Person person;
}

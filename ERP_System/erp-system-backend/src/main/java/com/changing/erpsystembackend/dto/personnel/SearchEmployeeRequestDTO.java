package com.changing.erpsystembackend.dto.personnel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchEmployeeRequestDTO {
    private Long id;
    private String name;
    private String department;
    private String position;
    private String gender;
    private Integer ageStart;
    private Integer ageEnd;
    private String status;
}
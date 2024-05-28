package com.changing.erpsystembackend.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String id;
    private String name;
    private String gender;
    private String nationality;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthday;
    private String birthplace;
    private String politicalStatus;
    private Long tel;
    private String email;
    private String department;
    private String position;
    private MultipartFile resume;
}
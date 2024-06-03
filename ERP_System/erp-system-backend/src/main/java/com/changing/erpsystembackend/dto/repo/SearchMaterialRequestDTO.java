package com.changing.erpsystembackend.dto.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchMaterialRequestDTO {
    private Long id;
    private String name;
}
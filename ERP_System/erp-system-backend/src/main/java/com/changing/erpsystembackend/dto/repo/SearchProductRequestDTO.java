package com.changing.erpsystembackend.dto.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductRequestDTO {
    private Long id;
    private String name;
}
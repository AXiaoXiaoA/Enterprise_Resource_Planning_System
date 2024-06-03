package com.changing.erpsystembackend.dto.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckMaterialOrderRequestDTO {
    private Long id;
    private String status;
    private Long repoEmployeeId;
}
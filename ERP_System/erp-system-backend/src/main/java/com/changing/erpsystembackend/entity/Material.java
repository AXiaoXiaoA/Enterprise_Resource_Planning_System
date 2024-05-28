package com.changing.erpsystembackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    private Long id;
    private String name;
    private Double price;
    private Integer inventory;
    private Integer remaining;
}

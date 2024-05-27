package com.changing.erpsystembackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Material {
    private Long id;
    private String name;
    private Double price;
    private Integer inventory;
    private Integer remaining;
}

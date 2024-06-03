package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.dto.repo.SearchMaterialRequestDTO;
import com.changing.erpsystembackend.entity.Material;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterialMapper {
    // insert
    void insertMaterial(Material material);

    // update
    void updateMaterial(Material material);

    // select
    Material findMaterialByName(@Param("name") String name);
    Material findMaterialById(@Param("id") Long id);
    Long findMaterialIdByName(@Param("name") String name);
    List<Material> findMaterial(SearchMaterialRequestDTO searchMaterialRequest);
}

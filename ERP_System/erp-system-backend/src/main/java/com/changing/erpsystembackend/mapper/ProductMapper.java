package com.changing.erpsystembackend.mapper;

import com.changing.erpsystembackend.dto.repo.SearchProductRequestDTO;
import com.changing.erpsystembackend.entity.Material;
import com.changing.erpsystembackend.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    // insert
    void insertProduct(Product product);

    // update
    void updateProduct(Product product);

    // select
    Product findProductByName(@Param("name") String name);
    Long findProductIdByName(@Param("name") String name);
    List<Product> findProduct(SearchProductRequestDTO searchProductRequest);
}

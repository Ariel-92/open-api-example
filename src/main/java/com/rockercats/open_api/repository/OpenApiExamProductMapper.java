package com.rockercats.open_api.repository;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OpenApiExamProductMapper {

//    @Select("SELECT * FROM product LIMIT 10")
    List<ProductDetailResponse> selectProductList();
}

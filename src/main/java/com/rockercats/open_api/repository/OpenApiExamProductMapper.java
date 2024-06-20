package com.rockercats.open_api.repository;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OpenApiExamProductMapper {
    List<ProductDetailResponse> selectProductList();
}

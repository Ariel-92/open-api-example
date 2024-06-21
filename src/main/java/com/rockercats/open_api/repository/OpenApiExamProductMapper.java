package com.rockercats.open_api.repository;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenApiExamProductMapper {
    List<ProductDetailResponse> selectProductList();
}

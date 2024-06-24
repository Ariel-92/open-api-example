package com.rockercats.open_api.repository;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OpenApiExamProductMapper {
    List<ProductDetailResponse> selectProductList(ProductDetailRequest productDetailRequest);
}

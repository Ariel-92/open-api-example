package com.rockercats.open_api.repository.product;

import com.rockercats.open_api.dto.product.ProductDetailRequest;
import com.rockercats.open_api.dto.product.ProductDetailResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenApiExamProductMapper {
    List<ProductDetailResponse> selectProductList(ProductDetailRequest productDetailRequest);
}

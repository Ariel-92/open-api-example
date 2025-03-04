package com.rockercats.open_api.repository;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.model.product.ProductReviewRequest;
import com.rockercats.open_api.model.product.ProductReviewResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDetailResponse> selectProductList(ProductDetailRequest productDetailRequest);

    List<ProductReviewResponse> selectProductReviewList(ProductReviewRequest productDetailRequest);
}

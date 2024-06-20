package com.rockercats.open_api.service;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductService {
    List<ProductDetailResponse> getProducts();
}

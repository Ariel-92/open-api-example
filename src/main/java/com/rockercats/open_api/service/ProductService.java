package com.rockercats.open_api.service;

import com.rockercats.open_api.model.product.ProductDetailResponse;

import java.util.List;


public interface ProductService {
    List<ProductDetailResponse> getProducts();
}

package com.rockercats.open_api.service;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.repository.OpenApiExamProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final OpenApiExamProductMapper openApiExamProductMapper;

    @Override
    public List<ProductDetailResponse> getProducts() {
        List<ProductDetailResponse> productDetailResponse = openApiExamProductMapper.selectProductList();
        return productDetailResponse;
    }
}

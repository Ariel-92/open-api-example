package com.rockercats.open_api.service;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.repository.OpenApiExamProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final OpenApiExamProductMapper openApiExamProductMapper;

    public List<ProductDetailResponse> getProducts() {
        // 상품 조회
        return openApiExamProductMapper.selectProductList();
    }
}

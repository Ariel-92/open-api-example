package com.rockercats.open_api.service;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.repository.OpenApiExamProductMapper;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final OpenApiExamProductMapper openApiExamProductMapper;
    private final BucketService bucketService;

    public ResponseEntity<List<ProductDetailResponse>> getProducts(ProductDetailRequest productDetailRequest, HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket(request);

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        List<ProductDetailResponse> productDetailResponseList = openApiExamProductMapper.selectProductList(productDetailRequest);
        return ResponseEntity.ok().body(productDetailResponseList);
    }
}
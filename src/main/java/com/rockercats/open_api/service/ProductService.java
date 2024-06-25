package com.rockercats.open_api.service;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.model.product.ProductReviewRequest;
import com.rockercats.open_api.model.product.ProductReviewResponse;
import com.rockercats.open_api.repository.ProductMapper;
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
    private final ProductMapper productMapper;
    private final BucketService bucketService;

    public ResponseEntity<List<ProductDetailResponse>> getProducts(ProductDetailRequest productDetailRequest, HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket(request);

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        List<ProductDetailResponse> productDetailResponseList = productMapper.selectProductList(productDetailRequest);
        return ResponseEntity.ok().body(productDetailResponseList);
    }

    public ResponseEntity<List<ProductReviewResponse>> getReview(ProductReviewRequest productReviewRequest, HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket(request);

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        List<ProductReviewResponse> productReviewResponseList = productMapper.selectProductReviewList(productReviewRequest);
        return ResponseEntity.ok().body(productReviewResponseList);
    }
}
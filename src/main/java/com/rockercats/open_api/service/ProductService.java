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
    private final UserAccessService userAccessService;

    public ResponseEntity<List<ProductDetailResponse>> getProducts(ProductDetailRequest productDetailRequest) {
        List<ProductDetailResponse> productDetailResponseList = productMapper.selectProductList(productDetailRequest);
        return ResponseEntity.ok().body(productDetailResponseList);
    }

    public ResponseEntity<List<ProductDetailResponse>> getProtectedProducts(ProductDetailRequest productDetailRequest, HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket();

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        ResponseEntity<Integer> userAccessCount = userAccessService.getUserAccessLog(request);

        // 유저별 일 접근회수제한 이상 접근시 429 에러 반환
        if (userAccessCount.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        // 유저 접근 로그 저장
        userAccessService.insertUserAccessLog(request);

        List<ProductDetailResponse> productDetailResponseList = productMapper.selectProductList(productDetailRequest);
        return ResponseEntity.ok().body(productDetailResponseList);
    }

    public ResponseEntity<List<ProductReviewResponse>> getReview(ProductReviewRequest productReviewRequest) {
        List<ProductReviewResponse> productReviewResponseList = productMapper.selectProductReviewList(productReviewRequest);
        return ResponseEntity.ok().body(productReviewResponseList);
    }

    public ResponseEntity<List<ProductReviewResponse>> getProtectedReview(ProductReviewRequest productReviewRequest, HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket();

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        ResponseEntity<Integer> userAccessCount = userAccessService.getUserAccessLog(request);

        // 유저별 일 접근회수제한 이상 접근시 429 에러 반환
        if (userAccessCount.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        // 유저 접근 로그 저장
        userAccessService.insertUserAccessLog(request);

        List<ProductReviewResponse> productReviewResponseList = productMapper.selectProductReviewList(productReviewRequest);
        return ResponseEntity.ok().body(productReviewResponseList);
    }
}
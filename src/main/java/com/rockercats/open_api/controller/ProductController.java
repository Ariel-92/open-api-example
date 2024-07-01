package com.rockercats.open_api.controller;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.model.product.ProductReviewRequest;
import com.rockercats.open_api.model.product.ProductReviewResponse;
import com.rockercats.open_api.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ProductController {
    private final ProductService productService;

    // public: UserAuth, protected: ApiKey
    @GetMapping("/public/products")
    public ResponseEntity<List<ProductDetailResponse>> getProducts(@RequestBody(required = false) ProductDetailRequest productDetailRequest) {
        return productService.getProducts(productDetailRequest);
    }

    @GetMapping( "/protected/products")
    public ResponseEntity<List<ProductDetailResponse>> getProtectedProducts(@RequestBody(required = false) ProductDetailRequest productDetailRequest,
                                                                   HttpServletRequest request) {
        return productService.getProtectedProducts(productDetailRequest, request);
    }

    @GetMapping("/public/review")
    public ResponseEntity<List<ProductReviewResponse>> getReview(@RequestBody(required = false) ProductReviewRequest productReviewRequest) {
        return productService.getReview(productReviewRequest);
    }

    @GetMapping( "/protected/review")
    public ResponseEntity<List<ProductReviewResponse>> getProtectedReview(@RequestBody(required = false) ProductReviewRequest productReviewRequest,
                                                                 HttpServletRequest request) {
        return productService.getProtectedReview(productReviewRequest, request);
    }
}
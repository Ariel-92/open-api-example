package com.rockercats.open_api.controller;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
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

    @GetMapping(value= {"/public/products", "/protected/products"})
    public ResponseEntity<List<ProductDetailResponse>> getProducts(@RequestBody(required = false) ProductDetailRequest productDetailRequest,
                                                                   HttpServletRequest request) {
        return productService.getProducts(productDetailRequest, request);
    }
}
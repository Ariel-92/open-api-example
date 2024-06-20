package com.rockercats.open_api.controller;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/public/products")
    public ResponseEntity<List<ProductDetailResponse>> getProducts() {
        List<ProductDetailResponse> productDetailResponse = productService.getProducts();
        return ResponseEntity.ok().body(productDetailResponse);

//    public ResponseEntity<String> getProducts() {
//        return ResponseEntity.ok("Greetings from a public endpoint!");
    }
}

package com.rockercats.open_api.controller;

import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.service.BucketService;
import com.rockercats.open_api.service.ProductService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    private final BucketService bucketService;

    @GetMapping("/public/products")
    public ResponseEntity<List<ProductDetailResponse>> getProducts(HttpServletRequest request) {

        Bucket bucket = bucketService.resolveBucket(request);
        log.info("접근 IP : {}", request.getRemoteAddr());

        if (bucket.tryConsume(1)) {
            log.info("정상처리 - 잔여토큰 : " + bucket.getAvailableTokens());
        } else {
            log.debug("트래픽 초과 요청 거부");
        }

        List<ProductDetailResponse> productDetailResponseList = productService.getProducts();
        return ResponseEntity.ok().body(productDetailResponseList);

//    public ResponseEntity<String> getProducts() {
//        return ResponseEntity.ok("Greetings from a public endpoint!");
    }
}

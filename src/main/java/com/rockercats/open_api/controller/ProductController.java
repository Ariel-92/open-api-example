package com.rockercats.open_api.controller;

import com.rockercats.open_api.model.product.ProductDetailRequest;
import com.rockercats.open_api.model.product.ProductDetailResponse;
import com.rockercats.open_api.service.BucketService;
import com.rockercats.open_api.service.ProductService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final BucketService bucketService;

    @GetMapping(value= {"/public/products", "/protected/products"})
    public ResponseEntity<List<ProductDetailResponse>> getProducts(@RequestBody(required = false) ProductDetailRequest productDetailRequest,
                                                                   HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket(request);
        logAccess(request, bucket);

        if (!bucket.tryConsume(1)) {
            log.debug("트래픽 초과 요청 거부");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        List<ProductDetailResponse> productDetailResponseList = productService.getProducts(productDetailRequest);
        return ResponseEntity.ok().body(productDetailResponseList);
    }

    private void logAccess(HttpServletRequest request, Bucket bucket) {
        log.info("접근 IP : {}", request.getRemoteAddr());
        log.info("정상처리 - 잔여토큰 : " + bucket.getAvailableTokens());
    }
}

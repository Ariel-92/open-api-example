package com.rockercats.open_api.controller;

import com.rockercats.open_api.service.BucketService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BucketController {

    private final BucketService bucketService;

    @GetMapping("/public/bucket")
    public ResponseEntity<String> bucketAccess(HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket(request);
        log.info("접근 IP : {}", request.getRemoteAddr());

        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok("정상처리 - 잔여토큰 : " + bucket.getAvailableTokens());
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("트래픽 초과 요청 거부");
        }
    }

}
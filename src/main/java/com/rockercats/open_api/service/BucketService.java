package com.rockercats.open_api.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rockercats.open_api.constants.Constants;

@Service
public class BucketService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket() {
        return cache.computeIfAbsent("sharedAPIBucket", this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        return Bucket.builder()
                // IP당 일 조회가능횟수 제한
                .addLimit(Bandwidth.classic(Constants.IP_DAILY_ACCESS_LIMIT, Refill.greedy(500, Duration.ofDays(1))))
                .build();
    }
}

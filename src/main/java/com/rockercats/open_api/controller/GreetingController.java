package com.rockercats.open_api.controller;

import com.rockercats.open_api.service.security.ApiJwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GreetingController {
    private final ApiJwtAuthService authService;

    @GetMapping("/public/greetings")
    public ResponseEntity<String> getPublicGreetings() {
        return ResponseEntity.ok("Greetings from a public endpoint!");
    }

    @GetMapping("/protected/greetings")
    public ResponseEntity<String> getProtectedGreetings() {
        return ResponseEntity.ok("Greetings from a proteted endpoint!");
    }

    @GetMapping("/public/getKey")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(authService.generateApiKey("/public/getKey"));
    }
}
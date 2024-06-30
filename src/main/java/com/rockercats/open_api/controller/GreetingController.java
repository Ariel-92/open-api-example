package com.rockercats.open_api.controller;

import com.rockercats.open_api.service.security.ApiJwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<String> getPublicKey(@RequestHeader("UserAuth") String userAuth, @RequestParam String apiPath) {
        return ResponseEntity.ok(authService.generateApiKey(apiPath, userAuth));
    }

    @GetMapping("/protected/keyTest")
    public ResponseEntity<String> getKeyTest() {
        return ResponseEntity.ok("Key Test Successfully!");
    }

    @GetMapping("/protected/errorTest")
    public ResponseEntity<String> getErrorTest() {
        return ResponseEntity.ok("Error Test Successfully!");
    }
}
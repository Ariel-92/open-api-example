package com.rockercats.open_api.controller;

import com.rockercats.open_api.dto.request.LogInRequestDto;
import com.rockercats.open_api.dto.response.LogInResponseDto;
import com.rockercats.open_api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login(@RequestBody LogInRequestDto request) throws Throwable{
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout";
    }

    @PostMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }
}

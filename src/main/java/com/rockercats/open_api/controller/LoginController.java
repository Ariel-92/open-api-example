package com.rockercats.open_api.controller;

import com.rockercats.open_api.dto.request.LogInRequestDto;
import com.rockercats.open_api.dto.response.LogInResponseDto;
import com.rockercats.open_api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login(@RequestBody LogInRequestDto request) throws Throwable{
        LogInResponseDto response;

        try {
            response = loginService.login(request);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(response);
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

package com.rockercats.open_api.global;

import com.rockercats.open_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private String secretKey = "secret_key";

    public String generateToken(User user) {
        // JWT 토큰 생성 로직

        return null;
    }

    public String getUsernameFromToken(String token) {
        // JWT 토큰 내 username 추출 로직

        return null;
    }

    public boolean validateToken(String token) {
        // JWT 토큰 유효성 검증 로직

        return false;
    }

}

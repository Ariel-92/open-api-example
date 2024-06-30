package com.rockercats.open_api.security.member;

import com.rockercats.open_api.entity.LoginLog;
import com.rockercats.open_api.global.JwtUtil;
import com.rockercats.open_api.security.ApiKeyAuth;
import com.rockercats.open_api.service.security.ApiJwtAuthService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.util.*;

@Component
@RequiredArgsConstructor
public class UserAuthExtractor {
    @Value("${jwt.api.secret-key}")
    private String accessSecretKey;

    private SecretKey secretKey;

    private final ApiJwtAuthService apiJwtAuthService;

    @PostConstruct
    protected void init() {
        this.secretKey = new SecretKeySpec(Base64.getEncoder().encode(accessSecretKey.getBytes()), "HmacSHA256");
    }

    public Optional<Authentication> extract(HttpServletRequest request) {
        String providedKey = request.getHeader("UserAuth");
        String uri = request.getRequestURI();

        LoginLog requestAuthInfo = JwtUtil.getUserAuthFromToken(providedKey, secretKey);

        try {
            LoginLog serverAuthInfo = apiJwtAuthService.getUserAuthInfo(requestAuthInfo.getId());

            // 정합성 검사
            if(providedKey == null &&
                    !requestAuthInfo.getId().equals(serverAuthInfo.getId()) ||
                    !requestAuthInfo.getUserId().equals(serverAuthInfo.getUserId()) ||
//                    !requestAuthInfo.getAccessTime().equals(serverAuthInfo.getAccessTime()) ||
//                    !requestAuthInfo.getExpiredTime().equals(serverAuthInfo.getExpiredTime()) ||
                    !requestAuthInfo.getRole().equals(serverAuthInfo.getRole())
            ) {
                return Optional.empty();
            }

            // 유효기간 검사
            if(requestAuthInfo.getExpiredTime().before(new Timestamp(System.currentTimeMillis()))) {
                return Optional.empty();
            }

            List<GrantedAuthority> roles = new LinkedList<>();

            roles.add(new SimpleGrantedAuthority("ROLE_" + serverAuthInfo.getRole()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(requestAuthInfo.getUserId(), null, roles);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Optional.of(new ApiKeyAuth(providedKey, roles));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

package com.rockercats.open_api.security;

import com.rockercats.open_api.entity.ApiKeys;
import com.rockercats.open_api.global.JwtUtil;
import com.rockercats.open_api.service.security.ApiJwtAuthService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthExtractor {

    @Value("${application.security.api-key}")
    private String apiKey;
    @Value("${jwt.api.secret-key}")
    private String accessSecretKey;

    private SecretKey secretKey;

    private final ApiJwtAuthService apiJwtAuthService;

    @PostConstruct
    protected void init() {
        this.secretKey = new SecretKeySpec(Base64.getEncoder().encode(accessSecretKey.getBytes()), "HmacSHA256");
    }

    public Optional<Authentication> extract(HttpServletRequest request) {
        String providedKey = request.getHeader("ApiKey");
        String uri = request.getRequestURI();

        ApiKeys apiKeys = JwtUtil.getApiKeysFromToken(providedKey, secretKey);

        ApiKeys compareKeyInfo = apiJwtAuthService.getApiKeyInfo(apiKeys.getApiUuid());
        if(providedKey == null && uri.equals(apiKeys.getApiPath()) && !apiKeys.getApiUuid().equals(compareKeyInfo.getApiUuid()) && apiKeys.getApiPath().equals(compareKeyInfo.getApiPath()) && apiKeys.getUserId().equals(compareKeyInfo.getApiPath())) {
            return Optional.empty();
        }

        return Optional.of(new ApiKeyAuth(providedKey, AuthorityUtils.NO_AUTHORITIES));
    }
}
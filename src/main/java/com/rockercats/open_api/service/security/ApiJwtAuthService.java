package com.rockercats.open_api.service.security;

import com.rockercats.open_api.entity.ApiKeys;
import com.rockercats.open_api.global.JwtUtil;
import com.rockercats.open_api.model.User;
import com.rockercats.open_api.repository.OpenApiExamApiKeysMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiJwtAuthService {

    @Value("${jwt.api.expired-time}")
    private String tokenTime;
    @Value("${jwt.api.secret-key}")
    private String accessSecretKey;

    private SecretKey secretKey;

    private final OpenApiExamApiKeysMapper apiKeysMapper;

    @PostConstruct
    protected void init() {
        this.secretKey = new SecretKeySpec(Base64.getEncoder().encode(accessSecretKey.getBytes()), "HmacSHA256");
    }

    public ApiKeys getApiKeyInfo(String token) {
        return null;
    }

    public String generateApiKey(String apiPath) {
        String uuid = UUID.randomUUID().toString();
        long tokenValidMiliseconds = 1000L * Long.parseLong(tokenTime);
        Date expiredDate = new Date(System.currentTimeMillis() + tokenValidMiliseconds);
        User user = new User();
        user.setUserId("user1");
        user.setId("ff366fdb-1a60-4efc-8be9-f05d4b01b969");
        user.setPassword("");
        user.setGrantType("A");

        ApiKeys apiKeys = new ApiKeys();
        apiKeys.setApiUuid(uuid);
        apiKeys.setUserId("user1");
        apiKeys.setExpiredTime(new Timestamp(expiredDate.getTime()));
        apiKeys.setApiPath(apiPath);
        apiKeysMapper.addApiKey(apiKeys);

        return JwtUtil.generateToken(uuid, user, expiredDate, apiPath, secretKey);
    }
}

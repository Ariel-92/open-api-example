package com.rockercats.open_api.service.security;

import com.rockercats.open_api.entity.ApiKeys;
import com.rockercats.open_api.entity.LoginLog;
import com.rockercats.open_api.global.JwtUtil;
import com.rockercats.open_api.entity.User;
import com.rockercats.open_api.repository.LoginLogMapper;
import com.rockercats.open_api.repository.OpenApiExamApiKeysMapper;
import com.rockercats.open_api.repository.UserMapper;
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
    private final LoginLogMapper loginLogMapper;

    @PostConstruct
    protected void init() {
        this.secretKey = new SecretKeySpec(Base64.getEncoder().encode(accessSecretKey.getBytes()), "HmacSHA256");
    }

    public ApiKeys getApiKeyInfo(String uuid) {
        return apiKeysMapper.getApiKeys(uuid);
    }

    public LoginLog getUserAuthInfo(String uuid) {
        return loginLogMapper.getLoginLogByUuid(uuid);
    }

    public String generateApiKey(String apiPath, String userAuth) {
        LoginLog loginInfo = JwtUtil.getUserAuthFromToken(userAuth, secretKey);

        String uuid = UUID.randomUUID().toString();
        long tokenValidMiliseconds = 1000L * Long.parseLong(tokenTime);
        Date expiredDate = new Date(System.currentTimeMillis() + tokenValidMiliseconds);

        ApiKeys apiKeys = new ApiKeys();
        apiKeys.setApiUuid(uuid);
        apiKeys.setUserId(loginInfo.getUserId());
        apiKeys.setExpiredTime(new Timestamp(expiredDate.getTime()));
        apiKeys.setApiPath(apiPath);
        apiKeysMapper.addApiKey(apiKeys);

        return JwtUtil.generateToken(uuid, loginInfo.getUserId(), expiredDate, apiPath, secretKey);
    }
}

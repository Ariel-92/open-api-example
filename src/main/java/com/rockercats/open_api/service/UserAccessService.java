package com.rockercats.open_api.service;

import com.rockercats.open_api.entity.ApiKeys;
import com.rockercats.open_api.model.user.UserAccessControlRequest;
import com.rockercats.open_api.repository.UserAccessLogControlMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rockercats.open_api.global.JwtUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class UserAccessService {
    @Value("${jwt.api.secret-key}")
    private String accessSecretKey;
    private SecretKey secretKey;
    private final UserAccessLogControlMapper userAccessLogControlMapper;

    @PostConstruct
    protected void init() {
        this.secretKey = new SecretKeySpec(Base64.getEncoder().encode(accessSecretKey.getBytes()), "HmacSHA256");
    }

    public ResponseEntity<Boolean> insertUserAccessLog (HttpServletRequest request) {
        String authHeader = request.getHeader("apiKey");
        ApiKeys apiKeys = JwtUtil.getApiKeysFromToken(authHeader, secretKey);

        try {
            UserAccessControlRequest userAccessControlRequest = new UserAccessControlRequest();
            userAccessControlRequest.setUserId(apiKeys.getUserId());
            userAccessControlRequest.setApiPath(apiKeys.getApiPath());

            userAccessLogControlMapper.insertUserAccessLog(userAccessControlRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(true);
    }

    public ResponseEntity<Integer> getUserAccessLog (HttpServletRequest request) {
        String authHeader = request.getHeader("apiKey");
        ApiKeys apiKeys = JwtUtil.getApiKeysFromToken(authHeader, secretKey);

        int result = 0;
        try {
            UserAccessControlRequest userAccessControlRequest = new UserAccessControlRequest();
            userAccessControlRequest.setUserId(apiKeys.getUserId());
            userAccessControlRequest.setApiPath(apiKeys.getApiPath());
            result = userAccessLogControlMapper.selectUserAccessLog(userAccessControlRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(result);
    }
}

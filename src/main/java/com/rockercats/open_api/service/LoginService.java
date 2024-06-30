package com.rockercats.open_api.service;

import com.rockercats.open_api.dto.request.LogInRequestDto;
import com.rockercats.open_api.dto.response.LogInResponseDto;
import com.rockercats.open_api.entity.LoginLog;
import com.rockercats.open_api.entity.User;
import com.rockercats.open_api.exception.CustomException;
import com.rockercats.open_api.exception.MemberErrorCode;
import com.rockercats.open_api.global.JwtUtil;
import com.rockercats.open_api.repository.LoginLogMapper;
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
public class LoginService {
    private final UserMapper examUserMapper;
    private final LoginLogMapper loginLogMapper;

    @Value("${jwt.api.access-token.expired-time}")
    private Long accessTokenTime;
    @Value("${jwt.api.refresh-token.expired-time}")
    private Long refreshTokenTime;
    @Value("${jwt.api.secret-key}")
    private String accessSecretKey;

    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        this.secretKey = new SecretKeySpec(Base64.getEncoder().encode(accessSecretKey.getBytes()), "HmacSHA256");
    }

    public LogInResponseDto login(LogInRequestDto loginRequestDto) throws CustomException {
        User userInfo = examUserMapper.getUserById(loginRequestDto.getUserId());

        if(!userInfo.getPassword().equals(loginRequestDto.getPassword())) {
            throw MemberErrorCode.MEMBER_NOT_FOUND.defaultException();
        }

        String uuid = UUID.randomUUID().toString();
        long tokenValidMilliseconds = 1000L * accessTokenTime;
        Date accessDate = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(accessDate.getTime() + tokenValidMilliseconds);
        String accessToken = JwtUtil.generateLoginToken(uuid, userInfo, accessDate, expiredDate, secretKey);

        long refreshTokenValidMilliseconds = 1000L * refreshTokenTime;
        Date refreshTokenExpiredDate = new Date(accessDate.getTime() + refreshTokenValidMilliseconds);
        String refreshToken = JwtUtil.generateLoginToken(uuid, userInfo,accessDate, refreshTokenExpiredDate, secretKey);

        LogInResponseDto responseDto = new LogInResponseDto();
        responseDto.setToken(accessToken);
        responseDto.setUserId(userInfo.getUserId());
        responseDto.setRefreshToken(refreshToken);

        LoginLog loginLog = new LoginLog();
        loginLog.setId(uuid);
        loginLog.setUserId(userInfo.getUserId());
        loginLog.setAccessTime(new Timestamp(accessDate.getTime()));
        loginLog.setExpiredTime(new Timestamp(expiredDate.getTime()));
        loginLog.setRefreshToken(refreshToken);
        loginLog.setRole(userInfo.getRole());

        loginLogMapper.insertLoginLog(loginLog);
        return responseDto;
    }
}

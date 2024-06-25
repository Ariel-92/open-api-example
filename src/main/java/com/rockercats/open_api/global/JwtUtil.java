package com.rockercats.open_api.global;

import com.rockercats.open_api.entity.ApiKeys;
import com.rockercats.open_api.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static String generateToken(String uuid, User user, Date expiredDate, String apiPath, SecretKey secretKey) {
        // JWT 토큰 생성 로직
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject("OPEN_API_EXAMPLE")
                .expiration(expiredDate)
                .issuedAt(new Date(System.currentTimeMillis()))
                .id(uuid)
                .claim("ID", uuid)
                .claim("USER_ID", user.getUserId())
                .claim("API_PATH", apiPath)
                .claim("GRANT_TYPE", user.getGrantType())
                .signWith(secretKey)
                .compact();
    }

    public static ApiKeys getApiKeysFromToken(String token, SecretKey secretKey) {
        // JWT  토큰 내 uuid 추출 로직
        ApiKeys apiKeys = new ApiKeys();

        try {
            apiKeys.setApiUuid(extractClaims(token, secretKey).get("ID").toString());
            apiKeys.setApiPath(extractClaims(token, secretKey).get("API_PATH").toString());
            apiKeys.setUserId(extractClaims(token, secretKey).get("USER_ID").toString());
            apiKeys.setExpiredTime(new Timestamp((new Date(Long.parseLong(extractClaims(token, secretKey).get("exp").toString()))).getTime()));
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return apiKeys;
    }

    public static String generateLoginToken(String uuid, User user, Date expiredDate, SecretKey secretKey) {
        // JWT 토큰 생성 로직
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject("OPEN_API_EXAMPLE")
                .expiration(expiredDate)
                .issuedAt(new Date(System.currentTimeMillis()))
                .id(uuid)
                .claim("ID", uuid)
                .claim("USER_ID", user.getUserId())
                .claim("GRANT_TYPE", user.getGrantType())
                .signWith(secretKey)
                .compact();
    }

    public static boolean validateToken(String token, SecretKey secretKey) {
        Logger log = LoggerFactory.getLogger(JwtUtil.class);
        // JWT 토큰 유효성 검증 로직
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private static Claims extractClaims(String token, SecretKey secretKey) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}

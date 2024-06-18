package com.rockercats.open_api.dto.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class User {
    private String userId;
    private String password;
    private String refreshToken;
    private String grantType;

    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }
}
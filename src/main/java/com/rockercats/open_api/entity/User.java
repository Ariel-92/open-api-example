package com.rockercats.open_api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class User {
    private String id;
    private String userId;
    private String password;
    private String grantType;

    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }
}
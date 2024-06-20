package com.rockercats.open_api.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class User {
    private Long id;
    private String username;
    private String password;

    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }
}
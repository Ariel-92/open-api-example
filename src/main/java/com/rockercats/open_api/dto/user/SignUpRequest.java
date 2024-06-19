package com.rockercats.open_api.dto.user;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String userId;
    private String password;
    private String grantType;
}

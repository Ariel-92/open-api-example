package com.rockercats.open_api.dto.user;

import com.rockercats.open_api.constants.GrantType;

public record SignInResponse (
    String name,
    GrantType type,
    String token) {
}

package com.rockercats.open_api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInResponseDto {
    private String token;
    private String refreshToken;
    private String userId;
}

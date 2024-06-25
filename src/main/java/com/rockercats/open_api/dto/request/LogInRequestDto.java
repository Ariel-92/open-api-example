package com.rockercats.open_api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInRequestDto {
    private String userId;
    private String password;
}
